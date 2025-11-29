package com.catalogx.productservice.implementation;

import com.catalogx.productservice.dto.CategoryRequest;
import com.catalogx.productservice.dto.CategoryResponse;
import com.catalogx.productservice.entity.Category;
import com.catalogx.productservice.exception.ResourceAlreadyExistsException;
import com.catalogx.productservice.exception.ResourceNotFoundException;
import com.catalogx.productservice.repository.CategoryRepository;
import com.catalogx.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {

        log.info("Recieved the request to create the Category"+ request.name());
        if(categoryRepository.existsByName(request.name()))
        {
            throw new ResourceNotFoundException("Category Already Exists"+ request.name());
        }

        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Category saved = categoryRepository.save(category);
        return toResponse(saved);
    }

    @Override
    public CategoryResponse getCategpry(Long id) {
        log.info("Found the category with this id"+id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found: "+ id));

        return toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        log.info("Found the all the categories");
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {

        log.info("Updated the category"+ id );
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found: "+ id));


        if(!category.getName().equals(request.name()) && categoryRepository.existsByName(request.name()))
        {
            throw new ResourceAlreadyExistsException("Category Name already exists: "+request.name());
        }

        category.setName(request.name());
        category.setDescription(request.description());

        return toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {

        log.info("Deleted the ID: "+ id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category Not Found: "+ id));

        if(category.getDescription() != null && !category.getProducts().isEmpty())
        {
            throw new IllegalArgumentException("Cannot delete category with the Product");
        }

        categoryRepository.delete(category);
    }

    private CategoryResponse toResponse(Category category){
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}
