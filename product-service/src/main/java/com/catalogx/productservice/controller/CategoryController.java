package com.catalogx.productservice.controller;

import com.catalogx.productservice.dto.APIResponse;
import com.catalogx.productservice.dto.CategoryRequest;
import com.catalogx.productservice.dto.CategoryResponse;
import com.catalogx.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<APIResponse<CategoryResponse>> createCategory(@Valid @RequestBody CategoryRequest request){
        return ResponseEntity.ok(APIResponse.success("Category created successfully", categoryService.createCategory(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> getCategory(@PathVariable Long id)
    {
        return ResponseEntity.ok(APIResponse.success("Category Found",categoryService.getCategpry(id)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryResponse>>> getAllCategories(){
        return ResponseEntity.ok(APIResponse.success("Found all the Categories",categoryService.getAllCategories()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponse>> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequest request){
        return ResponseEntity.ok(APIResponse.success("Updated the Category", categoryService.updateCategory(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<String>> deleteCategory(@PathVariable Long id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(APIResponse.success("Category Deleted Successfully",""));
    }
}
