package com.sorryisme.fmarket.service

import com.sorryisme.fmarket.domain.CartDetail
import com.sorryisme.fmarket.dto.request.CartRequestDto
import com.sorryisme.fmarket.dto.response.CartResponseDto
import com.sorryisme.fmarket.exception.NotFoundDataException
import com.sorryisme.fmarket.mapper.CartMapper
import com.sorryisme.fmarket.mapper.UserMapper
import com.sorryisme.fmarket.testUtils.DomainFixture
import spock.lang.Specification

class CartServiceTest extends Specification {
    UserMapper userMapper = Mock()
    CartMapper cartMapper = Mock()

    def "없는 유저일 경우 에러 발생"() {
        given:
        userMapper.findUserById(_ as Long) >> null
        CartService cartService = new CartService(cartMapper, userMapper)

        when:
        cartService.addCart(createCartRequestDto(), 1L)

        then:
        def e = thrown(NotFoundDataException.class)
        e.getMessage() == "찾을 수 없는 유저입니다"
    }

    def "이미 장바구니 데이터가 있어도 장바구니 생성 후 정상적으로 담아진다"() {
        given:
        userMapper.findUserById(_ as Long) >> DomainFixture.createUser()
        cartMapper.findCartIdByUserId(_ as Long) >> 5L
        cartMapper.insertCartDetail(_ as CartDetail) >> 1
        CartService cartService = new CartService(cartMapper, userMapper)

        when:
        CartResponseDto result = cartService.addCart(createCartRequestDto(), 1L)

        then:
        result.getCartId() == 5L
        result.getProductOptionId() == 1000L
        result.getQuantity() == 20
    }

    def "처음으로 장바구니에 담으려고 할 때 장바구니가 없다면 장바구니를 생성 후 제품 옵션을 담는다"() {
        given:
        userMapper.findUserById(_ as Long) >> DomainFixture.createUser()
        cartMapper.insertCartDetail(_ as CartDetail) >> 1
        CartService cartService = new CartService(cartMapper, userMapper)

        when:
        CartResponseDto result = cartService.addCart(createCartRequestDto(), 1L)

        then:
        result.getProductOptionId() == 1000L
        result.getQuantity() == 20
    }

    def "삭제 성공 시 id가 리턴된다"() {
        given:
        cartMapper.isExistCartDetailById(1L) >> true
        cartMapper.deleteCartDetailById(1L) >> 1
        CartService cartService = new CartService(cartMapper, userMapper);

        when:
        Long result = cartService.deleteCartDetail(1L)

        then:
        result == 1L
    }

    private static CartRequestDto createCartRequestDto() {
        return CartRequestDto.builder()
                .productOptionId(1000L)
                .quantity(20)
                .build();
    }
}