package com.sorryisme.fmarket.service

import com.sorryisme.fmarket.domain.Product
import com.sorryisme.fmarket.domain.ProductReview
import com.sorryisme.fmarket.dto.request.ProductReviewRequestDto
import com.sorryisme.fmarket.dto.request.ProductSearchDto
import com.sorryisme.fmarket.dto.response.MajorCategoryResponse
import com.sorryisme.fmarket.dto.response.ProductOptionResponseDto
import com.sorryisme.fmarket.dto.response.ProductResponseDto
import com.sorryisme.fmarket.dto.response.ProductReviewResponseDto
import com.sorryisme.fmarket.dto.response.SubcategoryResponse
import com.sorryisme.fmarket.exception.NotFoundDataException
import com.sorryisme.fmarket.mapper.MajorCategoryMapper
import com.sorryisme.fmarket.mapper.ProductMapper
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDateTime

class ProductServiceTest extends Specification {


    ProductMapper productMapper = Mock();
    MajorCategoryMapper majorCategoryMapper = Mock();
    ProductService productService = new ProductService(majorCategoryMapper, productMapper)

    def "카테고리 조회 시 모든 카테고리가 조회된다"() {
        given:
        def majorCategory = createMockMajorCategoryList()
        majorCategoryMapper.findMajorCategoryList() >> [majorCategory]

        when:
        def majCategoryList = productService.findMajorCategoryList()

        then:
        majCategoryList.size() == 1
        majorCategory.getSubcategories().size() == 2
    }

    def "모든 상품 리스트와 총 상품 개수를 조회한다"() {
        given:
        def product = DomainFixture.createProduct();
        productMapper.findAllProductList(_ as ProductSearchDto) >> [product];
        productMapper.countProducts(_ as ProductSearchDto) >> 1;

        when:
        Page<Product> result = productService.findAllProductList(createProductSearchDto());

        then:
        result.getContent().size() == 1
        result.getTotalElements() == 1
    }

    def "ID로 상품 조회 시 존재하지 않으면 예외가 발생한다"() {
        given:
        productMapper.findOneProductById(1L) >> null;

        when:
        productService.findProductById(1L);

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 제품입니다."
    }

    def "ID로 상품 조회 시 상품이 존재하면 데이터를 반환한다"() {
        given:
        productMapper.findOneProductById(1L) >> createProductResponseDto();

        when:
        ProductResponseDto result = productService.findProductById(1L);

        then:
        result.getId() == 1L
        result.getDescription() == "상품 설명"
        result.getOptions().size() == 2
        result.getReviews().size() == 2
    }

    def "리뷰 생성 시 상품이 존재하지 않으면 예외가 발생한다"() {
        given:
        productMapper.isExistProductById(1L) >> false
        def reviewRequestDto = createProductReviewRequestDto()


        when:
        productService.createReview(reviewRequestDto, 1L, 1L)

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 제품입니다."
    }

    def "리뷰 생성 시 성공적으로 저장된다"() {
        given:
        productMapper.isExistProductById(1L) >> true;
        def reviewRequestDto = createProductReviewRequestDto()

        when:
        ProductReview result = productService.createReview(reviewRequestDto, 1L, 1L);

        then:
        result.getProductId() == 1L
        result.getUserId() == 1L
        result.getReviewText() == "리뷰 작성 DTO"
        result.getRating() == 5
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

    private static ProductSearchDto createProductSearchDto() {
        def productSearchDto = ProductSearchDto.builder()
                .pageable(Pageable.ofSize(10))
                .query("")
                .build()

        return productSearchDto
    }

    private static ProductReviewRequestDto createProductReviewRequestDto() {
        return ProductReviewRequestDto.builder()
                .productId(1L)
                .reviewText("리뷰 작성 DTO")
                .rating(5)
                .build();
    }

    private static ProductResponseDto createProductResponseDto() {

        def options = List.of(
                ProductOptionResponseDto.builder()
                        .id(101L)
                        .optionName("옵션 1")
                        .originPrice(new BigDecimal("5000"))
                        .salePrice(new BigDecimal("4500"))
                        .build(),
                ProductOptionResponseDto.builder()
                        .id(102L)
                        .optionName("옵션 2")
                        .originPrice(new BigDecimal("7000"))
                        .salePrice(new BigDecimal("6500"))
                        .build()
        )

        def reviews = List.of(
                ProductReviewResponseDto.builder()
                        .reviewId(201L)
                        .productId(1L)
                        .userId(1001L)
                        .rating(5)
                        .reviewText("최고의 제품입니다.")
                        .createdAt(LocalDateTime.now())
                        .build(),
                ProductReviewResponseDto.builder()
                        .reviewId(202L)
                        .productId(1L)
                        .userId(1002L)
                        .rating(4)
                        .reviewText("좋은 제품입니다.")
                        .createdAt(LocalDateTime.now())
                        .build()
        )

        return ProductResponseDto.builder()
                .id(1L)
                .description("상품 설명")
                .thumbnail("썸네일 이미지 URL")
                .catalog("카탈로그 정보")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .options(options)
                .reviews(reviews)
                .build()
    }

}
