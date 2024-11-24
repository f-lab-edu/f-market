package com.sorryisme.fmarket.product.controller;

import com.sorryisme.fmarket.product.dto.response.MajorCategoryResponse;
import com.sorryisme.fmarket.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/category")
    public List<MajorCategoryResponse> findMajorCategoryList() {
        return productService.findMajorCategoryList();
    }

}
