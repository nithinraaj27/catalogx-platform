package com.catalogx.inventoryservice.repository;

import com.catalogx.inventoryservice.entity.InventoryReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, Long> {

    List<InventoryReservation> findBySku(String sku);

    List<InventoryReservation> findByOrderId(String orderId);
}
