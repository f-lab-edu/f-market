package com.sorryisme.fmarket.mapper

import com.sorryisme.fmarket.domain.Cart
import com.sorryisme.fmarket.domain.CartDetail
import com.sorryisme.fmarket.domain.User
import com.sorryisme.fmarket.testUtils.DomainFixture
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@MybatisTest
@ContextConfiguration
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CartMapperTest extends Specification {

    @Autowired
    private CartMapper cartMapper
    @Autowired
    private UserMapper userMapper
    User user

    def setup() {
        user = DomainFixture.createUser()
        userMapper.insertUser(user)
    }

    def "유저 아이디로 카트 아이디가 조회한다"() {
        given:
        Cart cart = DomainFixture.createCart(user.getId())
        cartMapper.insertCart(cart)
        when:
        long cartId = cartMapper.findCartIdByUserId(user.getId())
        then:
        cartId == cart.getId()
    }

    def "카트를 추가하면 저장 후 id를 생성한다"() {
        given:
        Cart cart = DomainFixture.createCart(user.getId())
        when:
        int result = cartMapper.insertCart(cart)
        then:
        result == 1
        cart.getId() != 0
    }

    def "카트 상세 데이터를 저장 후 id를 생성한다 "() {
        given:
        Cart cart = DomainFixture.createCart(user.getId())
        cartMapper.insertCart(cart)
        CartDetail cartDetail = DomainFixture.createCartDetail(cart.getId())
        when:
        int result = cartMapper.insertCartDetail(cartDetail);
        then:
        result == 1
        cartDetail.getId() != 0
    }

    def "카트 삭제가 성공되면 삭제된 데이터 수가 리턴된다"() {
        given:
        Cart cart = DomainFixture.createCart(user.getId())
        cartMapper.insertCart(cart)
        CartDetail cartDetail = DomainFixture.createCartDetail(cart.getId())
        cartMapper.insertCartDetail(cartDetail)
        when:
        int result = cartMapper.deleteCartDetailById(cartDetail.getId())
        then:
        result == 1
    }

    def "카트 세부 데이터가 있다면 true를 리턴한다"() {
        given:
        Cart cart = DomainFixture.createCart(user.getId())
        cartMapper.insertCart(cart)
        CartDetail cartDetail = DomainFixture.createCartDetail(cart.getId())
        cartMapper.insertCartDetail(cartDetail)
        when:
        boolean isExist = cartMapper.isExistCartDetailById(cartDetail.getId())
        then:
        isExist
    }
}