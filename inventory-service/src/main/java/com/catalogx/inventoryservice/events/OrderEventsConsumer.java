package com.catalogx.inventoryservice.events;

import com.catalogx.inventoryservice.dto.OrderEvent;
import com.catalogx.inventoryservice.entity.InventoryProjection;
import com.catalogx.inventoryservice.exception.ResourceNotFoundException;
import com.catalogx.inventoryservice.repository.InventoryProjectionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventsConsumer {

    private final InventoryProjectionRepository inventoryProjectionRepository;

    @KafkaListener(topics = "order-events", groupId = "inventory-events")
    public void handleOrderCreated(OrderEvent event){
        log.info("Recieved OrderCreatedEvent: {}", event);

        InventoryProjection inventoryProjection = inventoryProjectionRepository.findById(event.sku())
                .orElseThrow(() -> new ResourceNotFoundException("NO inventory Projection found for SKU"));

        switch(event.status()){
            case PENDING -> {
                log.info("Order {} reserved successfully. Deducting available stock.", event.orderId());
                inventoryProjection.setAvailableQuantity(inventoryProjection.getAvailableQuantity() - event.quantity());
                inventoryProjection.setReservedQuantity(inventoryProjection.getReservedQuantity() + event.quantity());
            }
            case CANCELLED-> {
                log.info("Order {} cancelled. Releasing reserved stock.", event.orderId());
                inventoryProjection.setAvailableQuantity(inventoryProjection.getAvailableQuantity() + event.quantity());
                inventoryProjection.setReservedQuantity(inventoryProjection.getReservedQuantity() - event.quantity());
            }

            case COMPLETED -> {
                log.info("Order {} completed. Finalizing stock reservation.", event.orderId());
                inventoryProjection.setReservedQuantity(inventoryProjection.getReservedQuantity() - event.quantity());
            }

            case REJECTED -> {
                log.info("Order {} rejected. No stock changes", event.orderId());
            }

            default -> {
                log.warn("Unhandled event status: {}", event.status());
            }
        }

        inventoryProjection.setUpdatedAt(LocalDateTime.now());
        inventoryProjectionRepository.save(inventoryProjection);

        log.info("Inventory Updated for sku {} after orderCreated Event", event.sku());

    }
}
