package com.sorryisme.fmarket.testUtils;

import com.sorryisme.fmarket.domain.Cart;
import com.sorryisme.fmarket.domain.CartDetail;
import com.sorryisme.fmarket.domain.User;

public class DomainFixture {
    public static User createUser() {
        return User.builder()
                .loginId("testUser")
                .password("xptmxmqlalfqjsgh!")
                .salt("testSalt")
                .name("테스트유저")
                .email("test@naver.com")
                .phoneNumber("01012345678")
                .build();
    }

    public static User createUpdateUser(long id) {
        return User.builder()
                .id(id)
                .name("업데이트된 이름")
                .email("updated_email@test.com")
                .phoneNumber("01199999999")
                .build();
    }

    public static Cart createCart(long userId) {
        return Cart.builder()
                .userId(userId)
                .build();
    }

    public static CartDetail createCartDetail(long cartId) {
        return CartDetail.builder()
                .cartId(cartId)
                .productOptionId(1L)
                .quantity(5)
                .build();
    }
}