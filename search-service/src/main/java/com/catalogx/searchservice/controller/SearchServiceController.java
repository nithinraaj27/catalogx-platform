package com.catalogx.searchservice.controller;

import com.catalogx.searchservice.dto.APIResponse;
import com.catalogx.searchservice.dto.SearchResult;
import com.catalogx.searchservice.entity.InventoryProjection;
import com.catalogx.searchservice.service.SearchService;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Slf4j
public class SearchServiceController {

    private final SearchService searchService;

    @GetMapping("/products")
    public ResponseEntity<APIResponse<List<SearchResult>>> searchProducts(@RequestParam String keyword)
    {
        return ResponseEntity.ok(APIResponse.success("Product Found", searchService.searchProducts(keyword)));
    }

    @GetMapping("/products/id/{productId}")
    public ResponseEntity<APIResponse<SearchResult>> getByProductId(@PathVariable Long productId){
        SearchResult result =searchService.getProductById(productId);
        return result != null ? ResponseEntity.ok(APIResponse.success("Product with the ID found",result)) : ResponseEntity.ok(APIResponse.error("Product with ID not Availabel", null));
    }

    @GetMapping("/products/sku/{sku}")
    public ResponseEntity<APIResponse<SearchResult>> getBySku(@PathVariable String sku)
    {
        SearchResult result = searchService.getProductBySku(sku);
        return result != null ? ResponseEntity.ok(APIResponse.success("Product with the SKU found",result)) : ResponseEntity.ok(APIResponse.error("Product with SKU not Availabel", null));
    }
}
