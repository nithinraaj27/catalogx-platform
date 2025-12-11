package com.catalogx.searchservice.implementation;

import com.catalogx.searchservice.dto.SearchResult;
import com.catalogx.searchservice.entity.InventoryProjection;
import com.catalogx.searchservice.entity.ProductProjection;
import com.catalogx.searchservice.repository.InventoryProjectionRepository;
import com.catalogx.searchservice.repository.ProductProjectionRepository;
import com.catalogx.searchservice.repository.SkuProductMappingRepository;
import com.catalogx.searchservice.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchServiceImpl implements SearchService {

    private final InventoryProjectionRepository inventoryProjectionRepository;
    private final ProductProjectionRepository productProjectionRepository;
    private final SkuProductMappingRepository skuProductMappingRepository;


    @Override
    public List<SearchResult> searchProducts(String keyword) {

        log.info("Search-service: searching product by keyword={}", keyword);

        List<ProductProjection> products = productProjectionRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .filter(p -> !Boolean.TRUE.equals(p.getDeleted()))
                .toList();

        return products.stream().map(this::toSearchResult).toList();
    }

    @Override
    public SearchResult getProductById(Long productId) {

        log.info("Search-service: fetching product join by productId={}", productId);

        ProductProjection product = productProjectionRepository.findById(productId).orElse(null);

        if(product == null || Boolean.TRUE.equals(product.getDeleted()))
        {
            return null;
        }

        return toSearchResult(product);
    }

    @Override
    public SearchResult getProductBySku(String sku) {

        log.info("Search-service: fetching product join by sku={}", sku);

        var mapping = skuProductMappingRepository.findById(sku).orElse(null);

        if(mapping == null || !mapping.getActive())
        {
            return null;
        }

        return getProductById(mapping.getProductId());
    }


    private SearchResult toSearchResult(ProductProjection product)
    {
        InventoryProjection inventory = inventoryProjectionRepository.findById(product.getSku()).orElse(null);

        return SearchResult.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .sku(product.getSku())
                .categoryId(product.getCategoryId())
                .attributeProjectionList(product.getAttributes())
                .deleted(product.getDeleted())
                .updatedAt(product.getUpdatedAt())

                .totalQuantity(inventory != null ? inventory.getTotalQuantity() : 0)
                .reservedQuantity(inventory != null? inventory.getReservedQuantity() : 0)
                .availableQuantity(inventory != null? inventory.getAvailableQuantity() : 0)
                .build();
    }
}
