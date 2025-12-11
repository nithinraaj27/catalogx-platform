package com.catalogx.searchservice.dto;

import java.time.LocalDateTime;

public record InventoryUpdateEvent(
        String sku,
        Integer totalQuantity,
        Integer reservedQuantity,
        Integer availableQuantity,
        LocalDateTime updatedAt,
        InventoryEventType inventoryEventType
) {}
