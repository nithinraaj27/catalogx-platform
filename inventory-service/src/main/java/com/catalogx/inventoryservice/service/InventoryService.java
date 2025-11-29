package com.catalogx.inventoryservice.service;

import com.catalogx.inventoryservice.dto.InventoryRequest;
import com.catalogx.inventoryservice.dto.InventoryResponse;
import com.catalogx.inventoryservice.dto.ReservationRequest;
import com.catalogx.inventoryservice.dto.ReservationResponse;
import com.catalogx.inventoryservice.entity.InventoryReservation;

import java.util.List;

public interface InventoryService {

    InventoryResponse createOrUpdate(InventoryRequest request);

    InventoryResponse getInventory(String sku);

    List<InventoryResponse> getAllInventory();

    ReservationResponse reserveStock(ReservationRequest reservationRequest);

    void releaseStock(String sku, int quantity);
}
