package com.catalogx.orderservice.dto;

public record OrderRequest(
        String sku,
        Integer quantity
) {
}
