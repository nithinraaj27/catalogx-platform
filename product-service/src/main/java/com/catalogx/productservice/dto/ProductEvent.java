package com.catalogx.productservice.dto;

import com.catalogx.productservice.entity.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ProductEvent (
        Long productId,
        String name,
        String description,
        BigDecimal price,
        String sku,
        Long categoryId,
        List<AttributeResponse> attributes,
        ProductEventType eventType,
        LocalDateTime eventTime
) {

    public static ProductEvent fromEntity(Product product, ProductEventType type) {

        return ProductEvent.builder()
                .productId(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sku(product.getSku())
                .categoryId(product.getCategory().getId())
                .attributes(
                        product.getAttributes()
                                .stream()
                                .map(a -> new AttributeResponse(a.getId(), a.getAttributeKey(), a.getAttributeValue()))
                                .toList()
                )
                .eventType(type)
                .eventTime(LocalDateTime.now())
                .build();
    }
}
