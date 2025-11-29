package com.catalogx.inventoryservice.implementation;

import com.catalogx.inventoryservice.dto.InventoryRequest;
import com.catalogx.inventoryservice.dto.InventoryResponse;
import com.catalogx.inventoryservice.dto.ReservationRequest;
import com.catalogx.inventoryservice.dto.ReservationResponse;
import com.catalogx.inventoryservice.entity.Inventory;
import com.catalogx.inventoryservice.entity.InventoryReservation;
import com.catalogx.inventoryservice.exception.ResourceNotFoundException;
import com.catalogx.inventoryservice.repository.InventoryRepository;
import com.catalogx.inventoryservice.repository.InventoryReservationRepository;
import com.catalogx.inventoryservice.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryReservationRepository reservationRepository;


    @Override
    @Transactional
    public InventoryResponse createOrUpdate(InventoryRequest request) {

        log.info("Request Recieved for Creating and Updating the Inventory");
        Inventory inventory = inventoryRepository.findBySku(request.sku())
                .orElse(Inventory.builder()
                        .sku(request.sku())
                        .totalQuantity(0)
                        .reservedQuantity(0)
                        .updatedAt(LocalDateTime.now())
                        .build());

        inventory.setTotalQuantity(request.totalQuantity());
        inventory.setUpdatedAt(LocalDateTime.now());

        Inventory saved = inventoryRepository.save(inventory);

        return inventoryRepository.save(inventory).toResponse();
    }

    @Override
    public InventoryResponse getInventory(String sku) {
        log.info("Request Recieved to find  the product: "+ sku);
        return inventoryRepository.findBySku(sku)
                .map(Inventory::toResponse)
                .orElseThrow(() -> new RuntimeException("SKU Not Found"));
    }

    @Override
    public List<InventoryResponse> getAllInventory() {
        log.info("Request Recieved to get all the Items");
        return inventoryRepository.findAll()
                .stream()
                .map(Inventory::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReservationResponse reserveStock(ReservationRequest request) {

        log.info("Request recived to validate the stock");

        Inventory inventory = inventoryRepository.findBySku(request.sku())
                .orElseThrow(() -> new ResourceNotFoundException("SKU not found"));

        int available = inventory.getTotalQuantity() - inventory.getReservedQuantity();

        if(available < request.quantity())
        {
            throw new ResourceNotFoundException("Insufficient stock for SKU: "+ request.sku());
        }

        inventory.setReservedQuantity(inventory.getReservedQuantity() + request.quantity());
        inventory.setUpdatedAt(LocalDateTime.now());
        inventoryRepository.save(inventory);

        InventoryReservation reservation = InventoryReservation.builder()
                .sku(request.sku())
                .quantityReserved(request.quantity())
                .orderId(request.orderId())
                .reservedAt(LocalDateTime.now())
                .build();

        return reservationRepository.save(reservation).toResponse();
    }

    @Override
    @Transactional
    public void releaseStock(String sku, int quantity) {

        log.info("Request recived to release the available stock");

        Inventory inventory = inventoryRepository.findBySku(sku)
                .orElseThrow(() -> new RuntimeException("SKU not found"));

        inventory.setReservedQuantity(
                inventory.getReservedQuantity() - quantity
        );
        inventory.setUpdatedAt(LocalDateTime.now());

        inventoryRepository.save(inventory);

    }
}
