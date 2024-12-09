package com.sorryisme.fmarket.dto.response;

import com.sorryisme.fmarket.domain.Cart;
import com.sorryisme.fmarket.domain.CartDetail;
import com.sorryisme.fmarket.dto.request.CartRequestDto;
import lombok.*;

@Getter
@Builder
public class CartResponseDto {
    private long id;
    private long cartId;
    private long productOptionId;
    private int quantity;

    public static CartResponseDto from(CartDetail cartDetail) {
        return CartResponseDto.builder()
                .id(cartDetail.getId())
                .cartId(cartDetail.getCartId())
                .productOptionId(cartDetail.getProductOptionId())
                .quantity(cartDetail.getQuantity())
                .build();
    }
}