package com.catalogx.cartservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_projection")
@Builder
public class InventoryProjection {

    @Id
    private String sku;

    private int totalQuantity;
    private int reservedQuantity;
    private int availableQuantity;

    private LocalDateTime updatedAt;
}
