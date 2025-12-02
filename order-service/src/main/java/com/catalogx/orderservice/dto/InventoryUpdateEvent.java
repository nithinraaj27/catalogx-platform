package com.catalogx.orderservice.dto;

import java.time.LocalDateTime;

public record InventoryUpdateEvent(
        String sku,
        Integer totalQuantity,
        Integer reservedQuantity,
        Integer availableQuantity,
        LocalDateTime updatedAt
) {
}
