package com.catalogx.inventoryservice.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long id,
        String sku,
        Integer quantityReserved,
        String orderId,
        LocalDateTime reservedAt
) {}
