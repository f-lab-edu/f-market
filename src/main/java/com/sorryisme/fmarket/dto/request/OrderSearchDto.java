package com.sorryisme.fmarket.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSearchDto {

    private Long userId;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "startPeriod는 yyyy-MM-dd 형식이어야 합니다.")
    private String startPeriod;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "endPeriod는 yyyy-MM-dd 형식이어야 합니다.")
    private String endPeriod;
    private Pageable pageable;

    public static OrderSearchDto from(OrderSearchDto searchDto, Pageable pageable, Long userId) {
        return OrderSearchDto.builder()
                .userId(userId)
                .startPeriod(searchDto.getStartPeriod())
                .endPeriod(searchDto.getEndPeriod())
                .pageable(pageable)
                .build();
    }

}
