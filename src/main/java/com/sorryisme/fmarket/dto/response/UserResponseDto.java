package com.sorryisme.fmarket.dto.response;

import com.sorryisme.fmarket.domain.User;
import com.sorryisme.fmarket.dto.request.UserRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserResponseDto {

    private Long id;
    private String loginId;
    private String name;
    private String email;
    private String phoneNumber;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

}
