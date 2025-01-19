package com.sorryisme.fmarket.mapper


import com.sorryisme.fmarket.domain.Order
import com.sorryisme.fmarket.domain.OrderDetail
import com.sorryisme.fmarket.dto.request.OrderSearchDto
import com.sorryisme.fmarket.dto.response.OrderResponseDto
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderMapperTest extends Specification {

    @Autowired
    private OrderMapper orderMapper

    OrderSearchDto searchDto = new OrderSearchDto(1L,
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            Pageable.ofSize(10))


    def "DTO 내용이 전달되면 주문 내역이 검색된다."() {

        when:
        List<Order> orders = orderMapper.findAllOrderList(searchDto)

        then:
        orders != null
        orders.size() >= 0
    }

    def "DTO 내용이 전달되면 총 데이터 수량이 검색된다"() {

        when:
        int count = orderMapper.countOrderList(searchDto)

        then:
        count >= 0
    }

    def "존재하는 orderId가 제공되면 true가 리턴된다"() {
        given:
        Long orderId = 1L

        when:
        boolean exists = orderMapper.isExistOrderById(orderId)

        then:
        exists
    }

    def "orderId와 상태값이 제공되면 정상적으로 상태가 변경된다"() {
        given:
        Long orderId = 1L
        String newStatus = "CANCELLED"

        when:
        int result = orderMapper.updateOrder(orderId, newStatus)
        OrderResponseDto orderResponseDto = orderMapper.findOrderById(orderId)

        then:
        result == 1
        newStatus.equals(orderResponseDto.getStatus())
    }


    def "orderId로 주문을 조회하면 자재 정보를 리턴한다"() {
        given:
        Long orderId = 1L

        when:
        OrderResponseDto order = orderMapper.findOrderById(orderId)
        println(order.getCreatedAt())

        then:
        order != null
        order.id == orderId
        order.orderDetails.size() >= 0
    }

    def "새로운 주문이 생성되면 생성된 주문의 ID가 반환된다"() {
        given:
        Order newOrder = DomainFixture.createOrder()

        when:
        int result = orderMapper.createOrder(newOrder)

        then:
        result == 1
        newOrder.getId() != null
    }

    def "주문 상세가 여러 개 생성되면 정상적으로 삽입된다"() {
        given:
        List<OrderDetail> orderDetails = DomainFixture.createOrderDetails()

        when:
        int result = orderMapper.createOrderDetail(orderDetails)

        then:
        result == orderDetails.size()
    }

}
