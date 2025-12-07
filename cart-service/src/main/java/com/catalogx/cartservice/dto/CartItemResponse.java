package com.catalogx.cartservice.dto;

public record CartItemResponse(
        String id,
        String sku,
        int quantity,
        double price
) {}
