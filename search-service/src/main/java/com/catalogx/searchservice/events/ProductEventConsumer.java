package com.catalogx.searchservice.events;

import com.catalogx.searchservice.dto.ProductEvent;
import com.catalogx.searchservice.entity.ProductAttributeProjection;
import com.catalogx.searchservice.entity.ProductProjection;
import com.catalogx.searchservice.entity.SkuProductMapping;
import com.catalogx.searchservice.repository.ProductProjectionRepository;
import com.catalogx.searchservice.repository.SkuProductMappingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductEventConsumer {

    private final ProductProjectionRepository productProjectionRepository;
    private final SkuProductMappingRepository mappingRepository;

    @KafkaListener(topics = "product-events", groupId = "search-events-group",containerFactory = "productKafkaListenerContainerFactory")
    public void consume(ProductEvent event)
    {
        log.info("Search-service received productEvent: {}", event);

        //Step 1 - Load Existing projection if exists
        ProductProjection projection = productProjectionRepository.findById(event.productId())
                .orElse(ProductProjection.builder()
                        .productId(event.productId())
                        .deleted(false)
                        .build());


        //Step 2 - Idempotency check
        if(projection.getLastEventTime() != null && projection.getLastEventTime().isAfter(event.eventTime()))
        {
            log.warn("Ignoring older event for productId={} eventTime={}", event.productId(), event.eventTime());
            return;
        }

        //Step 3 - Route by Event Type
        switch(event.eventType()){

            case PRODUCT_CREATED -> handleProductCreated(event, projection);

            case PRODUCT_UPDATED -> handleProductUpdated(event, projection);

            case PRODUCT_DELETED -> handleProductDeleted(event, projection);
        }

        //Step 4 - Update idempotency timestamp
        projection.setLastEventTime(event.eventTime());

        //Step 5 - Save it in projection table
        productProjectionRepository.save(projection);

        log.info("Product Projection updated for productId={}", projection.getProductId());
    }

    private void handleProductDeleted(ProductEvent event, ProductProjection projection) {
        log.info("Processing PRODUCTED_DELETED for productId={} ", event.productId());

        projection.setDeleted(true);
        projection.setDeletedAt(event.eventTime());
    }

    private void handleProductUpdated(ProductEvent event, ProductProjection projection) {

        log.info("Processing PRODUCT_UPDATED for productid={}", event.productId());

        String oldSku = projection.getSku();
        String newSku = event.sku();

        applyCommonFields(event, projection);

        if(!newSku.equals(oldSku))
        {
            log.info("SKU changes from '{}' to {} for productId={}", oldSku, newSku, event.productId());

            insertOrUpdateMapping(event);

            if(oldSku != null)
            {
                mappingRepository.findById(oldSku).ifPresent(map -> {
                    map.setActive(false);
                    mappingRepository.save(map);
                });
            }
        }
    }

    private void handleProductCreated(ProductEvent event, ProductProjection projection) {

        log.info("Processing PRODUCT_CREATED for productId={}", event.productId());

        applyCommonFields(event, projection);
        insertOrUpdateMapping(event);
    }

    private void insertOrUpdateMapping(ProductEvent event) {
        SkuProductMapping mapping = mappingRepository.findById(event.sku())
                .orElse(SkuProductMapping.builder()
                        .sku(event.sku())
                        .build());

        mapping.setProductId(event.productId());
        mapping.setActive(true);
        mapping.setUpdatedAt(event.eventTime());

        mappingRepository.save(mapping);

        log.info("Mapping updated: {} -> {}", event.sku(), event.productId());
    }

    private void applyCommonFields(ProductEvent event, ProductProjection projection) {

        projection.setName(event.name());
        projection.setDescription(event.description());
        projection.setPrice(event.price());
        projection.setSku(event.sku());
        projection.setCategoryId(event.categoryId());
        projection.setUpdatedAt(event.eventTime());

        if(projection.getCreatedAt() == null)
        {
            projection.setCreatedAt(event.eventTime());
        }


        projection.setAttributes(
                event.attributes().stream().map(a -> ProductAttributeProjection.builder()
                        .attributeId(a.id())
                        .key(a.key())
                        .value(a.value())
                        .build())
                        .collect(Collectors.toList())
        );

        projection.setDeleted(false);
    }
}
