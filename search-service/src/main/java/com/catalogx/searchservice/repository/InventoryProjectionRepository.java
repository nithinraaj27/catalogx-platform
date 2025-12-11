package com.catalogx.searchservice.repository;

import com.catalogx.searchservice.entity.InventoryProjection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InventoryProjectionRepository extends MongoRepository<InventoryProjection, String> {

    List<InventoryProjection> findBySkuContainingIgnoreCase(String keyword);
}
