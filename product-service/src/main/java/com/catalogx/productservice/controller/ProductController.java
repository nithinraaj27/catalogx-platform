package com.catalogx.productservice.controller;

import com.catalogx.productservice.dto.APIResponse;
import com.catalogx.productservice.dto.ProductRequest;
import com.catalogx.productservice.dto.ProductResponse;
import com.catalogx.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "Product Management APIs")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<APIResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest productRequest)
    {
        ProductResponse response = productService.createProduct(productRequest);
        return ResponseEntity.ok(APIResponse.success("Product Fetched Succesfully", response));
    }


    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> getProduct(@PathVariable Long id)
    {
        ProductResponse response = productService.getProduct(id);
        return ResponseEntity.ok(APIResponse.success("Found the right Product", response));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<ProductResponse>>> getAllProducts(){
        return ResponseEntity.ok(APIResponse.success("Found all the Product",productService.getAllProducts()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<ProductResponse>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request
    )
    {
        return ResponseEntity.ok(APIResponse.success("Updated Successfully", productService.updateProduct(id, request)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteProduct(@PathVariable Long id)
    {
        productService.deleteProduct(id);
        return ResponseEntity.ok(APIResponse.success("Product Deleted", "Successfully"));
    }


}
