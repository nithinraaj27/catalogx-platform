package com.catalogx.searchservice.events;

import com.catalogx.searchservice.dto.InventoryUpdateEvent;
import com.catalogx.searchservice.entity.InventoryProjection;
import com.catalogx.searchservice.entity.SkuProductMapping;
import com.catalogx.searchservice.repository.InventoryProjectionRepository;
import com.catalogx.searchservice.repository.SkuProductMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {

    private final InventoryProjectionRepository projectionRepository;
    private final SkuProductMappingRepository skuProductMappingRepository;

    @KafkaListener(topics = "inventory-events", groupId = "search-events-group", containerFactory = "inventoryKafkaListenerContainerFactory")
    public void consume(InventoryUpdateEvent inventoryUpdateEvent)
    {
        log.info("Search events received InventoryUpdateEvent: {}", inventoryUpdateEvent);

        // Step 1 Load projection or Create New
        InventoryProjection projection = projectionRepository.findById(inventoryUpdateEvent.sku())
                .orElse(InventoryProjection.builder()
                        .sku(inventoryUpdateEvent.sku())
                        .build());

        // Step 2 Idempotency Check
        if(projection.getLastEventTime() != null && projection.getLastEventTime().isAfter(inventoryUpdateEvent.updatedAt()))
        {
            log.warn("Ignoring older Inventory event for sku={} eventTime={}", inventoryUpdateEvent.sku(), inventoryUpdateEvent.updatedAt());
            return;
        }

        // Step 3 Update fields
        projection.setTotalQuantity(inventoryUpdateEvent.totalQuantity());
        projection.setReservedQuantity(inventoryUpdateEvent.reservedQuantity());
        projection.setAvailableQuantity(inventoryUpdateEvent.availableQuantity());
        projection.setUpdatedAt(inventoryUpdateEvent.updatedAt());

        // Step 4 Update Idempotency Timestamp
        projection.setLastEventTime(inventoryUpdateEvent.updatedAt());

        // Step 5 Save projection
        projectionRepository.save(projection);

        log.info("Search-events updated inventory projection for sku: {}", inventoryUpdateEvent.sku());

        // product join
        Optional<SkuProductMapping> mapping = skuProductMappingRepository.findById(inventoryUpdateEvent.sku());
        mapping.ifPresent(m -> log.info("Inventory update joined with productId={}", m.getProductId()));
    }
}
