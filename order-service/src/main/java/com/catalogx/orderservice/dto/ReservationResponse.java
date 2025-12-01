package com.catalogx.orderservice.dto;

import java.time.LocalDateTime;

public record ReservationResponse(
        Long reservationId,
        String sku,
        Integer quantityReserved,
        Long orderId,
        LocalDateTime reservedAt
) {
}
