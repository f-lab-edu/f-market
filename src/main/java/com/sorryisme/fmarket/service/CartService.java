
package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.domain.Cart;
import com.sorryisme.fmarket.domain.CartDetail;
import com.sorryisme.fmarket.domain.User;
import com.sorryisme.fmarket.dto.request.CartRequestDto;
import com.sorryisme.fmarket.dto.response.CartResponseDto;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.mapper.CartMapper;
import com.sorryisme.fmarket.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final UserMapper userMapper;

    @Transactional
    public CartResponseDto addCart(CartRequestDto cartRequestDto, Long userId) {

        User user = userMapper.findUserById(userId);
        if (user == null) throw new NotFoundDataException("찾을 수 없는 유저입니다");

        Long cartId = cartMapper.findCartIdByUserId(userId);
        //카트가 없을 경우 카트를 최초 생성
        if (cartId == null) {
            Cart cart = Cart.of(userId);
            cartMapper.insertCart(cart);
            cartId = cart.getId();
        }

        CartDetail cartDetail = CartDetail.from(cartRequestDto, cartId);
        cartMapper.insertCartDetail(cartDetail);

        return CartResponseDto.from(cartDetail);
    }

    public Long deleteCartDetail(Long id) {
        boolean isExist = cartMapper.isExistCartDetailById(id);
        if (!isExist) throw new NotFoundDataException("찾을 수 없는 장바구니입니다");
        cartMapper.deleteCartDetailById(id);
        return id;
    }
}