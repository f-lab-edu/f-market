package com.sorryisme.fmarket.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductReview {

    private Long reviewId;
    private Long productId;
    private Integer rating;
    private String reviewText;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
