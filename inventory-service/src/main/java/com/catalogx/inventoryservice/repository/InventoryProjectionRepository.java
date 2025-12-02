package com.catalogx.inventoryservice.repository;

import com.catalogx.inventoryservice.entity.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProjectionRepository extends JpaRepository<InventoryProjection, String> {
}
