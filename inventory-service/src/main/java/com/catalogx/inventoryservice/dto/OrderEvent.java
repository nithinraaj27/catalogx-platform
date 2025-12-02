package com.catalogx.inventoryservice.dto;

import java.time.LocalDateTime;

public record OrderEvent(
        Long orderId,
        String sku,
        Integer quantity,
        OrderStatus status,
        LocalDateTime timeStamp
) {
}
