package com.catalogx.searchservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sku_product_mapping")
public class SkuProductMapping {

    @Id
    private String sku;

    private Long productId;

    private Boolean active;
    private LocalDateTime updatedAt;
}
