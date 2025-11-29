package com.catalogx.inventoryservice.dto;

import java.time.LocalDateTime;

public record InventoryResponse(
        String sku,
        Integer totalQuantity,
        Integer reservedQuantity,
        Integer availableQuantity,
        LocalDateTime lastUpdatedAt
) {}
