package com.sorryisme.fmarket.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemRequestDto {

    @NotNull
    private Long productOptionId;

    @NotNull
    @Min(1)
    private Integer quantity;
}
