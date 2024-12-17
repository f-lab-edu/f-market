package com.sorryisme.fmarket.controller;

import com.sorryisme.fmarket.annotation.LoginUserId;
import com.sorryisme.fmarket.annotation.RequireLogin;
import com.sorryisme.fmarket.common.dto.ResponseDto;
import com.sorryisme.fmarket.domain.Product;
import com.sorryisme.fmarket.domain.ProductReview;
import com.sorryisme.fmarket.dto.request.ProductReviewRequestDto;
import com.sorryisme.fmarket.dto.request.ProductSearchDto;
import com.sorryisme.fmarket.dto.response.MajorCategoryResponse;
import com.sorryisme.fmarket.dto.response.ProductResponseDto;
import com.sorryisme.fmarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/category")
    public List<MajorCategoryResponse> findMajorCategoryList() {
        return productService.findMajorCategoryList();
    }

    @PostMapping("/products/search")
    public ResponseDto<Page<Product>> findAllProductList(
            @RequestBody(required = false) ProductSearchDto productSearchDto,
            @PageableDefault Pageable pageable
    ) {
        Page<Product> searchResult = productService.findAllProductList(ProductSearchDto.from(productSearchDto, pageable));
        return ResponseDto.success(searchResult);
    }

    @GetMapping("/products/{id}")
    public ResponseDto<ProductResponseDto> findProductById(@PathVariable Long id) {
        ProductResponseDto productResponseDto = productService.findProductById(id);
        return ResponseDto.success(productResponseDto);
    }

    @PostMapping("/{productId}/reviews")
    @RequireLogin
    public ResponseDto<ProductReview> createReview(@RequestBody ProductReviewRequestDto requestDto, @PathVariable Long productId, @LoginUserId Long userId) {
        ProductReview productReview = productService.createReview(requestDto, productId, userId);
        return ResponseDto.success(productReview);
    }
}
