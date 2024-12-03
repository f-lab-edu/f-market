package com.sorryisme.fmarket.service;

import com.sorryisme.fmarket.domain.Store;
import com.sorryisme.fmarket.domain.User;
import com.sorryisme.fmarket.dto.request.SellerRequestDto;
import com.sorryisme.fmarket.dto.request.UserRequestDto;
import com.sorryisme.fmarket.dto.request.UserUpdateRequestDto;
import com.sorryisme.fmarket.dto.response.SellerResponseDto;
import com.sorryisme.fmarket.dto.response.UserResponseDto;
import com.sorryisme.fmarket.exception.DuplicateDataException;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.exception.UpdateFailException;
import com.sorryisme.fmarket.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        validateExistUser(userRequestDto.getName(), userRequestDto.getPhoneNumber());
        User saveUser = insertUserByDto(userRequestDto);

        return UserResponseDto.from(saveUser);
    }

    public long updateUser(UserUpdateRequestDto userUpdateRequestDto, long userId) {

        boolean isExist = userMapper.isExistUserById(userId);
        if(!isExist) throw new NotFoundDataException("찾을 수 없는 유저입니다");

        User updateUser = User.from(userUpdateRequestDto, userId);
        int result = userMapper.updateUser(updateUser);

        if(result == 0) {
            throw new UpdateFailException("정보 수정에 실패했습니다.");
        }

        return userId;
    }

    @Transactional
    public SellerResponseDto createSeller(SellerRequestDto sellerRequestDto) {

        validateExistUser(sellerRequestDto.getName(), sellerRequestDto.getPhoneNumber());
        User saveUser = insertUserByDto(sellerRequestDto);

        Store saveStore = Store.from(sellerRequestDto, saveUser.getId());
        this.userMapper.insertStore(saveStore);

        return SellerResponseDto.from(saveUser, saveStore);
    }

    private void validateExistUser(String username, String phoneNumber) throws DuplicateDataException {
        boolean isExistUser = this.userMapper.isExistUser(username, phoneNumber);
        if (isExistUser) throw new DuplicateDataException("이미 등록된 유저입니다.");
    }

    private User insertUserByDto(UserRequestDto userRequestDto) {
        User saveUser = User.from(userRequestDto);
        this.userMapper.insertUser(saveUser);
        return saveUser;
    }


}
