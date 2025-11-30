package com.catalogx.inventoryservice;

import java.time.LocalDateTime;

public record InventoryUpdateEvent(
        String sku,
        Integer totalQuantity,
        Integer reservedQuantity,
        Integer availableQuantity,
        LocalDateTime updatedAt
) {
}
