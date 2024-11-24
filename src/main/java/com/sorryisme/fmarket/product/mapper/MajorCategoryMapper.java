package com.sorryisme.fmarket.product.mapper;

import com.sorryisme.fmarket.product.dto.response.MajorCategoryResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorCategoryMapper {

    List<MajorCategoryResponse> findMajorCategoryList();

    @Insert("INSERT INTO MAJOR_CATEGORY(category_name, description) VALUES (#{categoryName}, #{categoryName})")
    Long insertMajorCategory(String categoryName, String categoryDescription);

    @Insert("INSERT INTO SUBCATEGORY(major_category_id, category_name, description) VALUES (#{majorCategoryId}, #{categoryName}, #{description})")
    Long insertSubCategory(long majorCategoryId, String categoryName, String description);

}
