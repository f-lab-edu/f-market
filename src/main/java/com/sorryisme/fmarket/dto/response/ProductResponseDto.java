package com.sorryisme.fmarket.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {

    private Long id;
    private String description;
    private String thumbnail;
    private String catalog;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductOptionResponseDto> options;
    private List<ProductReviewResponseDto> reviews;

}
