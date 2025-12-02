package com.catalogx.orderservice.events;

import com.catalogx.orderservice.dto.InventoryUpdateEvent;
import com.catalogx.orderservice.entity.InventoryProjection;
import com.catalogx.orderservice.repository.InventoryProjectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryProjectionRepository inventoryProjectionRepository;

    @KafkaListener(topics = "inventory-events", groupId = "order-service")
    public void consume(InventoryUpdateEvent updateEvent)
    {
        log.info("Recieved InventoryUpdateEvent {} "+ updateEvent);

        InventoryProjection inventoryProjection = inventoryProjectionRepository.findById(updateEvent.sku())
                        .orElse(InventoryProjection.builder()
                                .sku(updateEvent.sku())
                                .build());

        inventoryProjection.setTotalQuantity(updateEvent.totalQuantity());
        inventoryProjection.setReservedQuantity(updateEvent.reservedQuantity());
        inventoryProjection.setAvailableQuantity(updateEvent.availableQuantity());
        inventoryProjection.setUpdatedAt(updateEvent.updatedAt());

        inventoryProjectionRepository.save(inventoryProjection);

        log.info("Inventory Projection Updated for {}", updateEvent.sku());
    }
}
