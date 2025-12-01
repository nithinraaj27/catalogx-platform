package com.catalogx.orderservice.dto;

import java.time.LocalDateTime;

public record OrderResponse(
        Long orderId,
        String sku,
        Integer quantity,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
