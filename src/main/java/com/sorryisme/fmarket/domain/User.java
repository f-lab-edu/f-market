package com.sorryisme.fmarket.domain;


import com.sorryisme.fmarket.dto.request.UserRequestDto;
import com.sorryisme.fmarket.dto.request.UserUpdateRequestDto;
import com.sorryisme.fmarket.utils.PasswordCipher;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private long id;
    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String salt;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;


    public static User from(UserRequestDto userRequestDto) {
        String salt = PasswordCipher.getSalt();
        String hashPassword = PasswordCipher.encrypt(userRequestDto.getPassword(), salt);

        return User.builder()
                .loginId(userRequestDto.getLoginId())
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .salt(salt)
                .password(hashPassword)
                .build();
    }

    public static User from(UserUpdateRequestDto userUpdateRequestDto, long userId) {
        return User.builder()
                .id(userId)
                .name(userUpdateRequestDto.getName())
                .email(userUpdateRequestDto.getEmail())
                .phoneNumber(userUpdateRequestDto.getPhoneNumber())
                .build();
    }
}
