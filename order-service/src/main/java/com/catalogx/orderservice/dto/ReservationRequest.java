package com.catalogx.orderservice.dto;

public record ReservationRequest(
        String sku,
        Integer quantity,
        String orderId
){}
