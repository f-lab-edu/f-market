package com.sorryisme.fmarket.controller;

import com.sorryisme.fmarket.annotation.RequireLogin;
import com.sorryisme.fmarket.common.SessionManager;
import com.sorryisme.fmarket.common.dto.ResponseDto;
import com.sorryisme.fmarket.dto.request.LoginDto;
import com.sorryisme.fmarket.dto.request.SellerRequestDto;
import com.sorryisme.fmarket.dto.request.UserRequestDto;
import com.sorryisme.fmarket.dto.request.UserUpdateRequestDto;
import com.sorryisme.fmarket.dto.response.SellerResponseDto;
import com.sorryisme.fmarket.dto.response.UserResponseDto;

import com.sorryisme.fmarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final SessionManager sessionManager;

    @PostMapping("/user/signup")
    public ResponseDto<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto responseDto = this.userService.createUser(userRequestDto);
        return ResponseDto.success(responseDto);
    }

    @PutMapping("/user/update/{userId}")
    @RequireLogin
    public ResponseDto<Long> updateUser(@RequestBody @Valid UserUpdateRequestDto userUpdateRequestDto, @PathVariable(required = true) long userId) {
        long id = this.userService.updateUser(userUpdateRequestDto, userId);
        return ResponseDto.success(id);
    }

    @PostMapping("/seller/signup")
    public ResponseDto<SellerResponseDto> createSeller(@RequestBody @Valid SellerRequestDto sellerRequestDto) {
        SellerResponseDto responseDto = this.userService.createSeller(sellerRequestDto);
        return ResponseDto.success(responseDto);
    }

    @PostMapping("/user/login")
    public ResponseDto<Long> login(@RequestBody LoginDto loginDto) {
        Long id = this.userService.login(loginDto.getLoginId(), loginDto.getPassword());
        sessionManager.setLoginUserId(id);
        return ResponseDto.success(id);
    }
}
