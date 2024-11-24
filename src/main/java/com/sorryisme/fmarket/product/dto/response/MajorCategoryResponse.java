package com.sorryisme.fmarket.product.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MajorCategoryResponse {

    private Long majorCategoryId;
    private String majorCategoryName;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SubcategoryResponse> subcategories;


}
