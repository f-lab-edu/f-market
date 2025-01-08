package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.domain.Inventory;
import com.sorryisme.fmarket.domain.Order;
import com.sorryisme.fmarket.dto.request.OrderSearchDto;
import com.sorryisme.fmarket.dto.response.OrderDetailResponseDto;
import com.sorryisme.fmarket.dto.response.OrderResponseDto;
import com.sorryisme.fmarket.enums.OrderStatus;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.mapper.InventoryMapper;
import com.sorryisme.fmarket.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    private final InventoryMapper inventoryMapper;

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

        OrderResponseDto orderResponseDto = orderMapper.findOrderById(orderId);

        if (orderResponseDto == null) throw new NotFoundDataException("찾을 수 없는 주문입니다.");
        if (!OrderStatus.PENDING.getValue().equals(orderResponseDto.getStatus())) throw new IllegalStateException("변경이 불가한 상태입니다");

        List<Inventory> orderedInventories = orderResponseDto.getOrderDetails()
                .stream()
                .map(OrderDetailResponseDto::toInventory)
                .toList();

        inventoryMapper.findStockQuantityForUpdate(orderedInventories);
        orderedInventories.forEach(inventoryMapper::increaseStockQuantity);

        orderMapper.updateOrder(orderId, OrderStatus.CANCELLED.getValue());
        return orderId;
    }
}
