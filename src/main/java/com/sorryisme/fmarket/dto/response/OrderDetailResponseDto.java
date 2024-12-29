package com.sorryisme.fmarket.dto.response;

import com.sorryisme.fmarket.domain.Inventory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailResponseDto {

    private Long id;
    private Long productOptionId;
    private Integer quantity;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Inventory toInventory() {
        return Inventory.builder()
                .productOptionId(productOptionId)
                .quantity(quantity)
                .build();
    }

}
