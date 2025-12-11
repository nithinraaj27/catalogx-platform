package com.catalogx.searchservice.service;

import com.catalogx.searchservice.dto.SearchResult;
import com.catalogx.searchservice.entity.InventoryProjection;

import java.util.List;

public interface SearchService {

   List<SearchResult> searchProducts(String keyword);

   SearchResult getProductById(Long productId);

   SearchResult getProductBySku(String sku);
}
