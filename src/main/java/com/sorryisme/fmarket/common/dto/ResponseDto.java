package com.sorryisme.fmarket.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto<T> {

    private Boolean success;
    private Integer statusCode;
    private String message;
    private T data;



    public static <T> ResponseDto<T> success(T data) {
        return success(data, "성공했습니다.");
    }

    public static <T> ResponseDto<T> success(T data, String message) {
        return ResponseDto.<T>builder()
                .success(true)
                .statusCode(200)
                .message(message)
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> fail(T data) {
        return fail(data, "실패했습니다");
    }

    public static <T> ResponseDto<T> fail(T data, String message) {
        return fail(500, data, message);
    }

    public static <T> ResponseDto<T> fail(int statusCode, T data, String message) {
        return ResponseDto.<T>builder()
                .success(false)
                .statusCode(statusCode)
                .message(message)
                .data(data)
                .build();
    }



}
