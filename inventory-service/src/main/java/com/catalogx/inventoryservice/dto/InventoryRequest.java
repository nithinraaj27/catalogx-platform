package com.catalogx.inventoryservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record InventoryRequest(

        @NotBlank(message = "SKU cannot be blank")
        String sku,

        @Min(value = 0, message = "Total quantity must be >= 0")
        Integer totalQuantity
) {
}
