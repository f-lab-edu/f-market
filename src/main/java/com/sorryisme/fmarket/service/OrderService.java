package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.annotation.IdempotencyKeyParam;
import com.sorryisme.fmarket.annotation.Idempotent;
import com.sorryisme.fmarket.domain.Inventory;
import com.sorryisme.fmarket.domain.Order;
import com.sorryisme.fmarket.domain.OrderDetail;
import com.sorryisme.fmarket.domain.ProductOption;
import com.sorryisme.fmarket.dto.request.OrderCreateDto;
import com.sorryisme.fmarket.dto.request.OrderItemRequestDto;
import com.sorryisme.fmarket.dto.request.OrderSearchDto;
import com.sorryisme.fmarket.dto.response.OrderDetailResponseDto;
import com.sorryisme.fmarket.dto.response.OrderResponseDto;
import com.sorryisme.fmarket.enums.OrderStatus;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.mapper.InventoryMapper;
import com.sorryisme.fmarket.mapper.OrderMapper;
import com.sorryisme.fmarket.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final InventoryMapper inventoryMapper;
    private final ProductMapper productMapper;


    public Page<Order> findAllOrderList(OrderSearchDto orderSearchDto) {
        List<Order> orderList = orderMapper.findAllOrderList(orderSearchDto);
        int total = orderMapper.countOrderList(orderSearchDto);

        return new PageImpl<>(orderList, orderSearchDto.getPageable(), total);
    }

    public OrderResponseDto findOneOrder(Long id) {
        OrderResponseDto orderResponseDto = orderMapper.findOrderById(id);
        if (orderResponseDto == null) throw new NotFoundDataException("찾을 수 없는 주문입니다.");

        return orderResponseDto;
    }

    public Long confirmOrder(Long orderId) {
        boolean isExistOrder = orderMapper.isExistOrderById(orderId);
        if (!isExistOrder) throw new NotFoundDataException("찾을 수 없는 주문입니다.");

        orderMapper.updateOrder(orderId, OrderStatus.COMPLETED.getValue());
        return orderId;
    }

    @Transactional
    public Long cancelOrder(Long orderId) {

        OrderResponseDto orderResponseDto = orderMapper.findOrderByIdForUpdate(orderId);

        if (orderResponseDto == null) throw new NotFoundDataException("찾을 수 없는 주문입니다.");
        if (!OrderStatus.PENDING.getValue().equals(orderResponseDto.getStatus()))
            throw new IllegalArgumentException("변경이 불가한 상태입니다");

        List<Inventory> orderedInventories = orderResponseDto.getOrderDetails()
                .stream()
                .map(OrderDetailResponseDto::toInventory)
                .toList();

        inventoryMapper.findStockQuantityForUpdate(orderedInventories);
        orderedInventories.forEach(inventoryMapper::increaseStockQuantity);

        orderMapper.updateOrder(orderId, OrderStatus.CANCELLED.getValue());
        return orderId;
    }

    @Idempotent
    @Transactional
    public Long createOrder(@IdempotencyKeyParam String idempotencyKey, Long userId, OrderCreateDto orderCreateDto) {

        // 1. 주문 생성
        List<ProductOption> productOptions = getProductOptions(orderCreateDto.getOrderItems());
        Map<Long, Integer> requestQuantityMap = orderCreateDto.toProductQuantityMap();

        Order order = Order.of(productOptions, requestQuantityMap, userId);
        orderMapper.createOrder(order);
        List<OrderDetail> orderDetails = order.toOrderDetails();
        orderMapper.createOrderDetail(orderDetails);

        // 2. 재고 수량 검증 및 업데이트
        List<Inventory> requestInventories = orderCreateDto.toInventoryList();
        List<Inventory> savedInventories = inventoryMapper.findStockQuantityForUpdate(requestInventories);
        List<Inventory> updateInventories = validateAndPrepareInventoryUpdates(savedInventories, requestInventories);

        inventoryMapper.updateStockQuantity(updateInventories);

        return order.getId();
    }

    private List<ProductOption> getProductOptions(List<OrderItemRequestDto> orderItems) {
        List<Long> productOptionIds = orderItems.stream().map(OrderItemRequestDto::getProductOptionId).toList();
        return productMapper.findProductOptionsByIds(productOptionIds);
    }

    private List<Inventory> validateAndPrepareInventoryUpdates(List<Inventory> savedInventory, List<Inventory> requestInventories) {
        Map<Long, Integer> savedInventoryMap = savedInventory.stream()
                .collect(Collectors.toMap(Inventory::getProductOptionId, Inventory::getQuantity));

        return requestInventories
                .stream()
                .map(requestInventory -> {
                    int savedQuantity = savedInventoryMap.getOrDefault(requestInventory.getProductOptionId(), 0);
                    int updateQuantity = savedQuantity - requestInventory.getQuantity();
                    if (updateQuantity < 0) throw new IllegalArgumentException("재고 수량이 충분하지 않습니다.");

                    return Inventory.of(requestInventory.getProductOptionId(), updateQuantity);
                }).toList();
    }

}
