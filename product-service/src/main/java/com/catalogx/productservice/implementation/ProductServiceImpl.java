package com.catalogx.productservice.implementation;

import com.catalogx.productservice.dto.AttributeResponse;
import com.catalogx.productservice.dto.ProductRequest;
import com.catalogx.productservice.dto.ProductResponse;
import com.catalogx.productservice.entity.Category;
import com.catalogx.productservice.entity.Product;
import com.catalogx.productservice.entity.ProductAttribute;
import com.catalogx.productservice.exception.DuplicateResourceException;
import com.catalogx.productservice.exception.ResourceAlreadyExistsException;
import com.catalogx.productservice.exception.ResourceNotFoundException;
import com.catalogx.productservice.repository.CategoryRepository;
import com.catalogx.productservice.repository.ProductRepository;
import com.catalogx.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        log.info("Creating product with SKU: {}", productRequest.sku());

        if(productRepository.existsBySku(productRequest.sku()))
        {
            throw new DuplicateResourceException( "Sku Already Exists: "+ productRequest.sku());
        }

        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not Found: "+ productRequest.categoryId()));

        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .sku(productRequest.sku())
                .category(category)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        if(productRequest.attributes() != null)
        {
            List<ProductAttribute> attributes = productRequest.attributes().stream()
                    .map(a -> ProductAttribute.builder()
                            .attributeKey(a.key())
                            .attributeValue(a.value())
                            .product(product)
                            .build())
                    .toList();
            product.setAttributes(attributes);
        }

        Product saved = productRepository.save(product);
        return toResponse(product);
    }

    @Override
    public ProductResponse getProduct(Long id) {
        log.info("Found product with id: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found: " + id));
        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        log.info("Found All the Products");
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {

        log.info("Updated the product with the id");
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found: "+ id));

        if(!product.getSku().equals(productRequest.sku()) && productRepository.existsBySku(productRequest.sku()))
        {
            throw new ResourceAlreadyExistsException("SKU Already Exists: "+ productRequest.sku());
        }

        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category Not found: "+ productRequest.categoryId()));

        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setPrice(productRequest.price());
        product.setSku(productRequest.sku());
        product.setCategory(category);
        product.setUpdatedAt(LocalDateTime.now());

        product.getAttributes().clear();
        productRequest.attributes().forEach(attrReq -> {
            ProductAttribute attr = ProductAttribute.builder()
                    .attributeKey(attrReq.key())
                    .attributeValue(attrReq.value())
                    .product(product)
                    .build();
            product.getAttributes().add(attr);
        });

        Product updated = productRepository.save(product);

        return toResponse(updated);
    }

    @Override
    public void deleteProduct(Long id) {

        log.info("Deleted the product"+ id);
        if(!productRepository.existsById(id))
        {
            throw new ResourceNotFoundException("Product Not found: "+ id);
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product product)
    {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getSku(),
                product.getCategory().getId(),
                product.getAttributes().stream()
                        .map(a -> new AttributeResponse(
                                a.getId(),
                                a.getAttributeKey(),
                                a.getAttributeValue()
                        ))
                        .toList(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
