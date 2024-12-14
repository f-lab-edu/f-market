package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.domain.Product;
import com.sorryisme.fmarket.domain.ProductReview;
import com.sorryisme.fmarket.dto.request.ProductReviewRequestDto;
import com.sorryisme.fmarket.dto.request.ProductSearchDto;
import com.sorryisme.fmarket.dto.response.MajorCategoryResponse;
import com.sorryisme.fmarket.dto.response.ProductResponseDto;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.mapper.MajorCategoryMapper;
import com.sorryisme.fmarket.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final MajorCategoryMapper majorCategoryMapper;
    private final ProductMapper productMapper;

    public List<MajorCategoryResponse> findMajorCategoryList() {
        return this.majorCategoryMapper.findMajorCategoryList();
    }

    public Page<Product> findAllProductList(ProductSearchDto productSearchDto) {

        List<Product> productList = productMapper.findAllProductList(productSearchDto);
        int total = productMapper.countProducts(productSearchDto);

        return new PageImpl<>(productList, productSearchDto.getPageable(), total);
    }

    public ProductResponseDto findProductById(Long id) {

        ProductResponseDto productResponseDto = productMapper.findOneProductById(id);
        if (productResponseDto == null) throw new NotFoundDataException("찾을 수 없는 제품입니다.");

        return productResponseDto;
    }

    public ProductReview createReview(ProductReviewRequestDto reviewRequestDto, Long productId, Long userId) {

        boolean isExistProduct = productMapper.isExistProductById(productId);
        if (!isExistProduct) throw new NotFoundDataException("찾을 수 없는 제품입니다.");

        ProductReview productReview = ProductReview.builder()
                .productId(productId)
                .userId(userId)
                .reviewText(reviewRequestDto.getReviewText())
                .rating(reviewRequestDto.getRating())
                .build();

        productMapper.insertProductReview(productReview);
        return productReview;
    }

}
