package com.sorryisme.fmarket.dto.request;

import com.sorryisme.fmarket.domain.Inventory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCreateDto {

    @NotNull
    @Size(min = 1)
    List<OrderItemRequestDto> orderItems;

    public Map<Long, Integer> toProductQuantityMap() {
        return orderItems.stream().collect(Collectors.toMap(OrderItemRequestDto::getProductOptionId, OrderItemRequestDto::getQuantity));
    }

    public List<Inventory> toInventoryList() {
        return orderItems.stream().map(orderItem -> Inventory.of(orderItem.getProductOptionId(), orderItem.getQuantity())).toList();
    }

}
