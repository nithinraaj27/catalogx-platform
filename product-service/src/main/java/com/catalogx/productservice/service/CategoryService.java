package com.catalogx.productservice.service;

import com.catalogx.productservice.dto.CategoryRequest;
import com.catalogx.productservice.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse getCategpry(Long id);
    List<CategoryResponse> getAllCategories();
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void deleteCategory(Long id);

}
