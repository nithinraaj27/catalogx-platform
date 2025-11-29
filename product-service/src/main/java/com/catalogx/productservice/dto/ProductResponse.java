package com.catalogx.productservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Long categoryId,
        List<AttributeResponse> attributes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt) {
}
