package com.sorryisme.fmarket.controller;

import com.sorryisme.fmarket.annotation.LoginUserId;
import com.sorryisme.fmarket.annotation.RequireLogin;
import com.sorryisme.fmarket.common.dto.ResponseDto;
import com.sorryisme.fmarket.dto.request.CartRequestDto;
import com.sorryisme.fmarket.dto.response.CartResponseDto;
import com.sorryisme.fmarket.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/add")
    @RequireLogin
    public ResponseDto<CartResponseDto> addCart(@RequestBody @Valid CartRequestDto cartRequestDto, @LoginUserId Long userId) {
        CartResponseDto responseDto = cartService.addCart(cartRequestDto, userId);
        return ResponseDto.success(responseDto);
    }

    @DeleteMapping("/cart/{id}")
    public ResponseDto<Long> deleteCart(@PathVariable Long id) {
        Long deleteId = cartService.deleteCartDetail(id);
        return ResponseDto.success(deleteId);
    }

}