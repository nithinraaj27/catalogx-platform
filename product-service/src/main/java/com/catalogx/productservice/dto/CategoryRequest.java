package com.catalogx.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotBlank(message = "Category Name should not be empty")
        @Size(min = 3, max = 255, message = "Category Name must be between 3 and 255 characters")
        String name,


        @Size(max = 1000, message = "Description cannto exceed 1000 characters")
        String description) {
}
