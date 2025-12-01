package com.catalogx.orderservice.events;

public record OrderStatusUpdatedEvent(
        Long orderId,
        String status,
        String reason
) {
}
