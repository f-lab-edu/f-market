package com.sorryisme.fmarket.testUtils;

import com.sorryisme.fmarket.domain.*;

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

    public static ProductReview createProductReview(Long userId) {
        return ProductReview.builder()
                .userId(userId)
                .productId(1L)
                .reviewText("좋은 제품입니다.")
                .rating(5)
                .build();
    }

    public static Product createProduct() {
        return Product.builder()
                .id(1L)
                .product_name("제품명1")
                .description("상품설명")
                .thumbnail("썸네일 주소")
                .catalog("카탈로그")
                .build();
    }
}