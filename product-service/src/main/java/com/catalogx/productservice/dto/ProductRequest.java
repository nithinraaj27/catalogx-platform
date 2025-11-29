package com.catalogx.productservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(

        @NotBlank(message = "Product Name cannot be empty")
        @Size(min = 3, max = 255, message = "Prduct name must be between 3 adn 255 characters")
        String name,

        @Size(max = 1000, message = "Description cannot exceed 1000 characters")
        String description,

        @NotNull(message = "Price is Required")
        @Positive(message = "Price must be greated than zero")
        BigDecimal price,

        @NotBlank(message = "SKU cannnot be empty")
        @Size(min = 3, max=255, message = "SKU must be between 3 and 255 characters")
        String sku,

        @NotNull(message = "Category ID is required!")
        Long categoryId,

        @Valid
        List<AttributeRequest> attributes) {
}
