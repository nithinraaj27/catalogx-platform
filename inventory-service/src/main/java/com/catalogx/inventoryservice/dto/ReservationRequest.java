package com.catalogx.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReservationRequest(

        @NotBlank(message = "SKU cannot be blank")
        String sku,

        @Min(value = 1, message = "Reserved quantity must be at least 1")
        Integer quantity,

        @NotBlank(message = "Order ID cannot be blank")
        String orderId
) {
}
