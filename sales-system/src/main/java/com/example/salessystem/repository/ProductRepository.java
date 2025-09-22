package com.example.salessystem.repository;

import com.example.salessystem.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Product entity.  Provides CRUD methods and can be
 * extended with custom queries if needed.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}