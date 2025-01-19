package com.sorryisme.fmarket.service

import com.sorryisme.fmarket.domain.Inventory
import com.sorryisme.fmarket.domain.Order
import com.sorryisme.fmarket.domain.OrderDetail
import com.sorryisme.fmarket.domain.ProductOption
import com.sorryisme.fmarket.dto.request.OrderCreateDto
import com.sorryisme.fmarket.dto.request.OrderItemRequestDto
import com.sorryisme.fmarket.dto.request.OrderSearchDto
import com.sorryisme.fmarket.dto.response.OrderDetailResponseDto
import com.sorryisme.fmarket.dto.response.OrderResponseDto
import com.sorryisme.fmarket.enums.OrderStatus
import com.sorryisme.fmarket.exception.NotFoundDataException
import com.sorryisme.fmarket.mapper.InventoryMapper
import com.sorryisme.fmarket.mapper.OrderMapper
import com.sorryisme.fmarket.mapper.ProductMapper
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDateTime

class OrderServiceTest extends Specification {

    OrderMapper orderMapper = Mock()
    InventoryMapper inventoryMapper = Mock()
    ProductMapper productMapper = Mock()
    OrderService orderService = new OrderService(orderMapper, inventoryMapper, productMapper)

    OrderCreateDto orderCreateDto
    List<ProductOption> productOptions
    List<Inventory> inventories

    private static final String UUID = "166f9067-2e5f-4932-e314-f438ae846d24"

    def setup() {
        orderCreateDto = new OrderCreateDto([
                new OrderItemRequestDto(1L, 3),
                new OrderItemRequestDto(2L, 2)
        ])

        productOptions = [
                new ProductOption(1L, 101L, "Option1", new BigDecimal("1000.00"), new BigDecimal("900.00"), LocalDateTime.now(), LocalDateTime.now()),
                new ProductOption(2L, 102L, "Option2", new BigDecimal("2000.00"), new BigDecimal("1800.00"), LocalDateTime.now(), LocalDateTime.now())
        ]

        inventories = DomainFixture.createInventories()
    }


    def "dto 제공되면 페이징 정보가 포함된 주문정보가 전달된다"() {

        given:
        OrderSearchDto orderSearchDto = createOrderSearchDto()
        List<Order> orders = [DomainFixture.createOrder()]
        orderSearchDto.getPageable() >> Pageable.ofSize(10)
        orderMapper.findAllOrderList(orderSearchDto) >> orders
        orderMapper.countOrderList(orderSearchDto) >> orders.size()

        when:
        Page<Order> result = orderService.findAllOrderList(orderSearchDto)

        then:
        result.getContent().size() == orders.size()
        result.getTotalElements() == orders.size()
    }

    def "ID로 주문 조회 시 존재하면 데이터를 반환한다"() {
        given:
        OrderResponseDto orderResponseDto = createOrderResponseDto()
        orderMapper.findOrderById(_ as Long) >> orderResponseDto

        when:
        OrderResponseDto result = orderService.findOneOrder(1L)

        then:
        result.getId() == orderResponseDto.getId()
        result.getUserId() == orderResponseDto.getUserId()
        result.getTotalAmount() == orderResponseDto.getTotalAmount()
        result.getOrderDetails().size() >= 1
    }

    def "주문 조회시 주문이 존재하지 않으면 예외가 발생한다"() {
        given:
        orderMapper.findOrderById(_ as Long) >> null

        when:
        OrderResponseDto result = orderService.findOneOrder(1L)

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 주문입니다."
    }

    def "주문 확정 시 정상적으로 확정되면 orderId가 리턴된다"() {
        given:
        orderMapper.isExistOrderById(_ as Long) >> true
        orderMapper.updateOrder(1L, _ as String) >> 1L

        when:
        Long result = orderService.confirmOrder(1L)

        then:
        result == 1L
    }

    def "주문 확정 시 주문이 없을 경우 에러가 발생한다"() {
        given:
        orderMapper.isExistOrderById(_ as Long) >> false
        orderMapper.updateOrder(1L, _ as String) >> 1L

        when:
        orderService.confirmOrder(1L)

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 주문입니다."
    }


    def "주문취소 시 정상적으로 orderId를 리턴한다"() {
        given:
        Long orderId = 1L
        OrderResponseDto orderResponseDto = createOrderResponseDto()

        orderMapper.findOrderById(orderId) >> orderResponseDto
        inventoryMapper.findStockQuantityForUpdate(_ as List<Inventory>) >> [Mock(Inventory)]
        inventoryMapper.increaseStockQuantity(_ as Inventory) >> 1
        orderMapper.updateOrder(orderId, OrderStatus.CANCELLED.getValue()) >> 1

        when:
        Long result = orderService.cancelOrder(orderId)

        then:
        result == orderId
    }

    def "주문취소 시 주문이 없을 경우 에러를 발생시킨다"() {
        given:
        Long orderId = 1L
        orderMapper.findOrderById(orderId) >> null

        when:
        orderService.cancelOrder(orderId)

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 주문입니다."
    }

    def "주문취소 시 주문 상태가 변경 완료 상태 일때 에러가 발생된다."() {
        given:
        Long orderId = 1L
        OrderResponseDto orderResponseDto = Mock()
        orderResponseDto.getStatus() >> OrderStatus.COMPLETED.getValue()

        orderMapper.findOrderById(orderId) >> orderResponseDto

        when:
        orderService.cancelOrder(orderId)

        then:
        def e = thrown(IllegalStateException.class)
        e.getMessage() == "변경이 불가한 상태입니다"
    }


    def "createOrder는 주문을 생성하고 재고를 업데이트한다"() {
        given:
        orderCreateDto = new OrderCreateDto([
                new OrderItemRequestDto(1L, 1),
                new OrderItemRequestDto(2L, 2)
        ])

        productMapper.findProductOptionsByIds(_ as List<Long>) >> productOptions
        inventoryMapper.findStockQuantityForUpdate(_ as List<Inventory>) >> inventories
        orderMapper.createOrder(_ as Order) >> 1
        orderMapper.createOrderDetail(_ as List<OrderDetail>) >> 2
        inventoryMapper.updateStockQuantity(_ as List<Inventory> ) >> 2

        when:
        orderService.createOrder(UUID, 1L, orderCreateDto)

        then:
        1 * orderMapper.createOrder(_)
        1 * orderMapper.createOrderDetail(_)
        1 * inventoryMapper.updateStockQuantity(_)
    }

    def "createOrder는 재고 부족 시 예외를 발생시킨다"() {
        given:
        productMapper.findProductOptionsByIds(_ as List<Long>) >> productOptions
        inventoryMapper.findStockQuantityForUpdate(_ as List<Inventory>) >> inventories
        orderMapper.createOrder(_ as Order) >> 1
        orderMapper.createOrderDetail(_ as List<OrderDetail>) >> 2
        inventoryMapper.updateStockQuantity(_ as List<Inventory> ) >> 2

        when:
        orderService.createOrder(UUID, 1L, orderCreateDto)

        then:
        def e = thrown(IllegalArgumentException.class)
        e.getMessage() == "재고 수량이 충분하지 않습니다."
    }

    private static OrderSearchDto createOrderSearchDto() {
        def orderSearchDto = OrderSearchDto.builder()
                .userId(1L)
                .startPeriod("2025-01-01")
                .endPeriod("2025-12-31")
                .pageable(Pageable.ofSize(10))
                .build()

        return orderSearchDto
    }

    private static OrderResponseDto createOrderResponseDto() {
        return OrderResponseDto.builder()
                .id(1L)
                .userId(1L)
                .status(OrderStatus.PENDING.getValue())
                .totalAmount(new BigDecimal(10000))
                .orderDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .orderDetails([createOrderDetailResponseDto()] as List<OrderDetailResponseDto>)
                .build()
    }

    private static OrderDetailResponseDto createOrderDetailResponseDto() {
        return OrderDetailResponseDto.builder()
                .id(1L)
                .productOptionId(1L)
                .quantity(5)
                .price(new BigDecimal(5000))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build()
    }
}
