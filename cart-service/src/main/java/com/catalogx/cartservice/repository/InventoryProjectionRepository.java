package com.catalogx.cartservice.repository;

import com.catalogx.cartservice.entity.InventoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryProjectionRepository extends JpaRepository<InventoryProjection, String> {
}
