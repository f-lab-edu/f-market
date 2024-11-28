package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.dto.response.MajorCategoryResponse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MajorCategoryMapper {

    List<MajorCategoryResponse> findMajorCategoryList();
}
