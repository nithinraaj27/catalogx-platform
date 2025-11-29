package com.catalogx.productservice.dto;

import java.time.LocalDateTime;

public record APIResponse<T>(
        String status,
        String message,
        T data,
        LocalDateTime timeStamp
) {

    public static <T> APIResponse<T> success(String message, T data)
    {
        return new APIResponse<>("SUCCESS", message, data, LocalDateTime.now());
    }

    public static <T> APIResponse<T> error(String message, T data)
    {
        return new APIResponse<>("ERROR", message, data, LocalDateTime.now());
    }
}
