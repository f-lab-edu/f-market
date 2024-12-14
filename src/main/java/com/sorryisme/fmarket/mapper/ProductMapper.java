package com.sorryisme.fmarket.mapper;

import com.sorryisme.fmarket.domain.Product;
import com.sorryisme.fmarket.domain.ProductReview;
import com.sorryisme.fmarket.dto.request.ProductReviewRequestDto;
import com.sorryisme.fmarket.dto.request.ProductSearchDto;
import com.sorryisme.fmarket.dto.response.MajorCategoryResponse;
import com.sorryisme.fmarket.dto.response.ProductResponseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findAllProductList(ProductSearchDto productSearchDto);
    int countProducts(ProductSearchDto productSearchDto);
    boolean isExistProductById(Long productId);
    int insertProductReview(ProductReview productReview);
    ProductResponseDto findOneProductById(Long productId);
}
