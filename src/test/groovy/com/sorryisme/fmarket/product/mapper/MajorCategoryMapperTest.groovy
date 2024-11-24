package com.sorryisme.fmarket.product.mapper

import com.sorryisme.fmarket.product.dto.response.MajorCategoryResponse
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MajorCategoryMapperTest extends Specification {

    @Autowired
    private MajorCategoryMapper majorCategoryMapper;

    def "메인 카테고리 조회 시 메인카테고리와 서브카테고리가 모두 조회된다"() {
        given:
            majorCategoryMapper.insertMajorCategory("대분류", "대분류 설명")
            majorCategoryMapper.insertSubCategory(1, "중분류", "중분류 설명")
            majorCategoryMapper.insertSubCategory(1, "중분류1", "중분류 설명1")
        when:
            List<MajorCategoryResponse> majorCategoryList = majorCategoryMapper.findMajorCategoryList()
        then:
            majorCategoryList.size() == 1
            majorCategoryList.get(0).getSubcategories().size() == 2
    }

}
