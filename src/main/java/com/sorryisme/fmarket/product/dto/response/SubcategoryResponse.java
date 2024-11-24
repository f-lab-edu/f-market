package com.sorryisme.fmarket.product.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubcategoryResponse {

    private Long subcategoryId;
    private String categoryName;
    private Long majorCategoryId;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
