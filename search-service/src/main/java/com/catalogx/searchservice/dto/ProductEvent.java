package com.catalogx.searchservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductEvent(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Long categoryId,
        List<AttribureResponse> attributes,
        ProductEventType eventType,
        LocalDateTime eventTime
) {
}
