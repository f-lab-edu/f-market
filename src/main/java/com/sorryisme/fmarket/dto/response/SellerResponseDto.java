package com.sorryisme.fmarket.dto.response;

import com.sorryisme.fmarket.domain.Store;
import com.sorryisme.fmarket.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SellerResponseDto extends UserResponseDto {

    private String storeName;
    private String businessNumber;
    private String logoUrl;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public static SellerResponseDto from(User user, Store store) {
        return SellerResponseDto.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .name(user.getName())
                .email(user.getEmail())
                .storeName(store.getStoreName())
                .businessNumber(store.getBusinessNumber())
                .phoneNumber(store.getBusinessNumber())
                .logoUrl(store.getLogoUrl())
                .build();
    }


}
