package com.catalogx.cartservice.dto;

public record CartItemRequest(
        String sku,
        int quantity,
        double price
) {}
