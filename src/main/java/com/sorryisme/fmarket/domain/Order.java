package com.sorryisme.fmarket.domain;

import com.sorryisme.fmarket.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Order {

    private Long id;
    private Long userId;
    private String status;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<OrderDetail> orderDetails;

    public List<OrderDetail> toOrderDetails() {
        return getOrderDetails().stream()
                .map(orderDetail -> OrderDetail.builder()
                        .id(orderDetail.getId())
                        .orderId(id)
                        .productOptionId(orderDetail.getProductOptionId())
                        .quantity(orderDetail.getQuantity())
                        .price(orderDetail.getPrice())
                        .build()
                ).toList();
    }

    public static Order of(List<ProductOption> productOptions, Map<Long, Integer> reqeuestQuantityMap, Long userId) {
        List<OrderDetail> orderDetails = productOptions.stream()
                .map(option -> {
                    int quantity = reqeuestQuantityMap.getOrDefault(option.getId(), 0);

                    return OrderDetail.builder()
                            .productOptionId(option.getId())
                            .price(option.getSalePrice())
                            .quantity(quantity)
                            .build();
                }).toList();

        return Order.builder()
                .userId(userId)
                .status(OrderStatus.PENDING.getValue())
                .totalAmount(calculateTotalAmount(orderDetails))
                .orderDetails(orderDetails)
                .build();
    }

    private static BigDecimal calculateTotalAmount(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

