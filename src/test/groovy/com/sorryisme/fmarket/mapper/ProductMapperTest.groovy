package com.sorryisme.fmarket.mapper

import com.sorryisme.fmarket.domain.Product
import com.sorryisme.fmarket.domain.ProductReview
import com.sorryisme.fmarket.domain.User
import com.sorryisme.fmarket.dto.request.ProductSearchDto
import com.sorryisme.fmarket.dto.response.ProductResponseDto
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification


@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductMapperTest extends Specification {

    @Autowired
    private ProductMapper productMapper

    @Autowired
    private UserMapper userMapper

    User user = DomainFixture.createUser()
    ProductReview productReview
    ProductSearchDto searchDto = new ProductSearchDto("제품",1, 101, Pageable.ofSize(10))

    def "DTO 내용이 전달되면 페이지 내용이 검색된다"() {

        when:
        List<Product> productList = productMapper.findAllProductList(searchDto)

        then:
        productList != null
        productList.size() >= 0
    }

    def "DTO 내용이 전달되면 총 데이터 수량이 검색된다"() {

        when:
        int productCount = productMapper.countProducts(searchDto)

        then:
        productCount >= 0
    }

    def "존재하는 productId가 제공되면 true가 리턴된다"() {
        given:
        Long productId = 1L

        when:
        boolean exists = productMapper.isExistProductById(productId)

        then:
        exists
    }

    def "리뷰가 추가되면 id가 생성된다"() {
        given:
        userMapper.insertUser(user)
        productReview = DomainFixture.createProductReview(user.getId())

        when:
        int result = productMapper.insertProductReview(productReview)

        then:
        result == 1
        productReview.getReviewId() == 1L
    }

    def "productId로 제품을 조회하면 자재 정보를 리턴한다"() {
        given:
        userMapper.insertUser(user)
        productReview = DomainFixture.createProductReview(user.getId())
        productMapper.insertProductReview(productReview)
        Long productId = 1L

        when:
        ProductResponseDto product = productMapper.findOneProductById(productId)

        then:
        product != null
        product.id == productId
        product.getOptions().size() >= 1
        product.getReviews().size() >= 1
    }
}
