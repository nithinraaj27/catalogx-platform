package com.catalogx.searchservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_projection")
public class ProductProjection {

    @Id
    private Long productId;

    private String name;
    private String description;
    private BigDecimal price;

    private String sku;
    private Long categoryId;

    private List<ProductAttributeProjection> attributes;

    private Boolean deleted;
    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private LocalDateTime lastEventTime;

}
