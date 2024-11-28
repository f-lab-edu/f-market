package com.sorryisme.fmarket.service

import com.sorryisme.fmarket.dto.response.MajorCategoryResponse
import com.sorryisme.fmarket.dto.response.SubcategoryResponse
import com.sorryisme.fmarket.mapper.MajorCategoryMapper
import spock.lang.Specification

class ProductServiceTest extends Specification {

    def "카테고리 조회 시 모든 카테고리가 조회된다"() {
        given:
        MajorCategoryMapper majorCategoryMapper = Mock()
        def majorCategory = createMockMajorCategoryList()
        majorCategoryMapper.findMajorCategoryList() >> [majorCategory]
        ProductService productService = new ProductService(majorCategoryMapper)

        when:
        def majCategoryList = productService.findMajorCategoryList()

        then:
        majCategoryList.size() == 1
        majorCategory.getSubcategories().size() == 2
    }

    private static MajorCategoryResponse createMockMajorCategoryList() {
        def subCategory = SubcategoryResponse.builder()
                .subcategoryId(1)
                .majorCategoryId(1)
                .categoryName("중분류")
                .categoryName("중분류 설명")
                .build();

        def subCategory2 = SubcategoryResponse.builder()
                .subcategoryId(2)
                .majorCategoryId(1)
                .categoryName("중분류")
                .categoryName("중분류 설명")
                .build();

        return MajorCategoryResponse.builder()
                .majorCategoryId(1)
                .majorCategoryName("대분류")
                .description("대분류 설명")
                .subcategories([subCategory, subCategory2])
                .build()
    }
}
