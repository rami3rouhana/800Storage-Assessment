package com.example.salessystem.controller;

import com.example.salessystem.model.Product;
import com.example.salessystem.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing CRUD endpoints for products.  The base path is
 * /api/products.  Responses use standard HTTP status codes: 200 OK for
 * successful reads, 201 Created for successful creations and 200 OK for
 * updates.  Errors are handled by the service layer using ResponseStatusException.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retrieve all products.
     */
    @GetMapping
    public List<Product> getProducts() {
        return productService.findAll();
    }

    /**
     * Create a new product.  Returns the created entity with its generated ID.
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update an existing product.
     */
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.update(id, product);
    }
}