package com.sorryisme.fmarket.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequestDto {

    @NotNull(message = "제품을 선택해주세요")
    private Long productOptionId;

    @NotNull(message = "수량을 입력해주세요")
    @Positive(message = "수량은 양수만 입력 가능합니다")
    @Min(1)
    private Integer quantity;
}