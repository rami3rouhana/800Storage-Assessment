package com.example.salessystem.repository;

import com.example.salessystem.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Sale entity.
 */
@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
}