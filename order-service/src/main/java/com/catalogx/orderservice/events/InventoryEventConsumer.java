package com.catalogx.orderservice.events;

import com.catalogx.orderservice.entity.InventoryProjection;
import com.catalogx.orderservice.repository.InventoryProjectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryProjectionRepository inventoryProjectionRepository;

    @KafkaListener(topics = "inventory-events", groupId = "order-service")
    public void consume(InventoryUpdateEvent updateEvent)
    {
        log.info("Recieved Inventory Update{} "+ updateEvent);

        InventoryProjection inventoryProjection = InventoryProjection.builder()
                .sku(updateEvent.sku())
                .totalQuantity(updateEvent.totalQuantity())
                .reservedQuantity(updateEvent.reservedQuantity())
                .availableQuantity(updateEvent.availableQuantity())
                .updatedAt(updateEvent.updatedAt())
                .build();

        inventoryProjectionRepository.save(inventoryProjection);
    }
}
