package com.catalogx.searchservice.dto;

import com.catalogx.searchservice.entity.ProductAttributeProjection;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record SearchResult(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Long categoryId,
        List<ProductAttributeProjection> attributeProjectionList,

        Integer totalQuantity,
        Integer reservedQuantity,
        Integer availableQuantity,

        Boolean deleted,
        LocalDateTime updatedAt
){}
