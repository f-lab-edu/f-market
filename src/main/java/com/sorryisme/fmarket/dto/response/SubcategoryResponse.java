package com.sorryisme.fmarket.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
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
