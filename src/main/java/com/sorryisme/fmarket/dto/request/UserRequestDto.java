package com.sorryisme.fmarket.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserRequestDto {

    @NotBlank(message = "아이디를 입력해주세요")
    private String loginId;

    @NotBlank(message = "비밀번호 입력해주세요")
    @Length(min = 4, message = "패스워드는 최소 4자리 이상이여야합니다.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요")
    private String name;

    @Email(message = "이메일 형식에 맞게 입력해주세요")
    @NotBlank(message = "이메일을 입력해주세요 입력해주세요")
    private String email;

    @NotBlank(message = "전화번호 입력해주세요")
    private String phoneNumber;


}

