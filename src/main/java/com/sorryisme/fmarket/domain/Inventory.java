package com.sorryisme.fmarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class Inventory {

    private Long productOptionId;
    private Integer quantity;

    public static Inventory of(Long productOptionId, Integer quantity) {
        return Inventory.builder()
                .productOptionId(productOptionId)
                .quantity(quantity)
                .build();
    }
}
