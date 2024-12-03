package com.sorryisme.fmarket.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SellerRequestDto extends UserRequestDto {

    @NotBlank(message = "상점 이름은 반드시 작성해주세요")
    private String storeName;

    @NotBlank(message = "사업자 번호를 입력해주세요")
    private String businessNumber;
    private String logoUrl;
    private String description;

}
