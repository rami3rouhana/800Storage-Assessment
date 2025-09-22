package com.example.salessystem.service;

import com.example.salessystem.model.Product;
import com.example.salessystem.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * Business logic related to products.  Provides CRUD operations and hides
 * the underlying repository from controllers.  Throws a ResponseStatusException
 * when a product cannot be found so that controllers can translate this into
 * an appropriate HTTP response.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Fetch all products.
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Find a single product by ID or throw 404.
     */
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    /**
     * Create a new product.  The ID is ignored and overwritten by the database.
     */
    public Product create(Product product) {
        product.setId(null);
        return productRepository.save(product);
    }

    /**
     * Update an existing product.  If no product exists with the given ID a 404 is thrown.
     */
    public Product update(Long id, Product updated) {
        Product existing = findById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        return productRepository.save(existing);
    }
}