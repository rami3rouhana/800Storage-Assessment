package com.example.salessystem.repository;

import com.example.salessystem.model.SaleTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for SaleTransaction entity.
 */
@Repository
public interface SaleTransactionRepository extends JpaRepository<SaleTransaction, Long> {
}