package com.catalogx.cartservice.events;

import com.catalogx.cartservice.dto.InventoryUpdateEvent;
import com.catalogx.cartservice.entity.InventoryProjection;
import com.catalogx.cartservice.repository.InventoryProjectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventsConsumer {

    private final InventoryProjectionRepository inventoryProjectionRepository;

    @KafkaListener(topics = "inventory-events", groupId = "cart-events-group")
    public void consume(InventoryUpdateEvent inventoryUpdateEvent)
    {
        log.info("Card-events received InventoryUpdateEvent: {}", inventoryUpdateEvent);

        InventoryProjection projection = inventoryProjectionRepository.findById(inventoryUpdateEvent.sku())
                .orElse(InventoryProjection.builder()
                        .sku(inventoryUpdateEvent.sku())
                        .build());

        projection.setTotalQuantity(inventoryUpdateEvent.totalQuantity());
        projection.setReservedQuantity(inventoryUpdateEvent.reservedQuantity());
        projection.setAvailableQuantity(inventoryUpdateEvent.availableQuantity());
        projection.setUpdatedAt(inventoryUpdateEvent.updatedAt());

        inventoryProjectionRepository.save(projection);

        log.info("Cart-events update projection for {}", inventoryUpdateEvent.sku());
    }
}
