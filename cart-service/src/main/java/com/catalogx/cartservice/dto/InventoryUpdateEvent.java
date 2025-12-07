package com.catalogx.cartservice.dto;

import java.time.LocalDateTime;

public record InventoryUpdateEvent (
    String sku,
    int totalQuantity,
    int reservedQuantity,
    int availableQuantity,
    LocalDateTime updatedAt,
    InvenntoryEventType invenntoryEventType
){}
