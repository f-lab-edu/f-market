package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.dto.response.MajorCategoryResponse;
import com.sorryisme.fmarket.mapper.MajorCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MajorCategoryMapper majorCategoryMapper;

    public List<MajorCategoryResponse> findMajorCategoryList() {
        return this.majorCategoryMapper.findMajorCategoryList();
    }

}
