package com.catalogx.searchservice.repository;

import com.catalogx.searchservice.entity.ProductProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductProjectionRepository extends MongoRepository<ProductProjection, Long> {

    List<ProductProjection> findByNameContainingIgnoreCase(String keyword);

    Optional<ProductProjection> findBySku(String sku);
}
