package com.sorryisme.fmarket.testUtils;

import com.sorryisme.fmarket.domain.*;
import com.sorryisme.fmarket.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    public static Inventory createInventory(Long productOptionId, Integer quantity) {
        return Inventory.builder()
                .productOptionId(productOptionId)
                .quantity(quantity)
                .build();
    }

    public static Order createOrder() {
        return Order.builder()
                .userId(1L)
                .status(OrderStatus.PENDING.getValue())
                .orderDate(LocalDateTime.now())
                .totalAmount(new BigDecimal(10000))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static List<OrderDetail> createOrderDetails() {
         OrderDetail orderDetail = OrderDetail.builder()
                 .orderId(1L)
                 .productOptionId(1L)
                 .price(new BigDecimal(100))
                 .quantity(5)
                 .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .orderId(1L)
                .productOptionId(1L)
                .price(new BigDecimal(100))
                .quantity(5)
                .build();

        return List.of(orderDetail, orderDetail2);
    }

    public static List<Inventory> createInventories() {
        Inventory inventory = createInventory(1L, 1);
        Inventory inventory2 = createInventory(2L, 2);
        return List.of(inventory, inventory2);
    }
}