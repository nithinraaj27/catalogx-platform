package com.catalogx.orderservice.dto;

import com.catalogx.orderservice.entity.OrderStatus;

import java.time.LocalDateTime;

public record OrderEvent(
        Long orderId,
        String sku,
        Integer quantity,
        OrderStatus status,
        LocalDateTime timeStamp
) {

}
