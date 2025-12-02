package com.catalogx.inventoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name= "inventory_projection")
@Data
public class InventoryProjection {

    @Id
    private String sku;

    private Integer totalQuantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;

    private LocalDateTime updatedAt;
}
