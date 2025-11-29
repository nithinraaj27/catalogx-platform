package com.catalogx.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AttributeRequest(

        @NotBlank(message = "Attribute key cannot be empty")
        @Size(min = 1, max = 255)
        String key,

        @NotBlank(message = "Attribute value cannot be empty")
        @Size(min = 1, max = 255)
        String value) {
}
