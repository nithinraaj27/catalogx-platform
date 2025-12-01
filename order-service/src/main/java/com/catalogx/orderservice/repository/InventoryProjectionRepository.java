package com.catalogx.orderservice.repository;

import com.catalogx.orderservice.entity.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProjectionRepository extends JpaRepository<InventoryProjection, String> {
}
