package com.catalogx.searchservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "inventory_projection")
public class InventoryProjection {

    @Id
    private String sku;

    private Integer totalQuantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private LocalDateTime updatedAt;

    private LocalDateTime lastEventTime;

}
