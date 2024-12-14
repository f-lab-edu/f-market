package com.sorryisme.fmarket.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchDto {

    private String query;
    private Integer majorCategory;
    private Integer subcategory;
    private Pageable pageable;


    public static ProductSearchDto from(ProductSearchDto searchDto, Pageable pageable) {
        return ProductSearchDto.builder()
                .query(searchDto.getQuery())
                .majorCategory(searchDto.getMajorCategory())
                .subcategory(searchDto.getSubcategory())
                .pageable(pageable)
                .build();
    }

}
