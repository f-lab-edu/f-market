package com.sorryisme.fmarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Cart {
    private long id;
    private long userId;

    public static Cart of(long userId) {
        return Cart.builder().userId(userId).build();
    }
}