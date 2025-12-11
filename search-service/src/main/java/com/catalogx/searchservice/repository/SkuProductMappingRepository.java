package com.catalogx.searchservice.repository;

import com.catalogx.searchservice.entity.SkuProductMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SkuProductMappingRepository extends MongoRepository<SkuProductMapping, String> {

    List<SkuProductMapping> findByProductId(Long productId);

    List<SkuProductMapping> findByActiveTrue();
}
