package com.catalogx.orderservice.events;

public record OrderCreatedEvent(
        Long orderId,
        String sku,
        Integer quantity,
        String status
) {

}
