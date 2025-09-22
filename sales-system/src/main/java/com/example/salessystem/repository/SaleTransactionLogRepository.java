package com.example.salessystem.repository;

import com.example.salessystem.model.SaleTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SaleTransactionLogRepository extends JpaRepository<SaleTransactionLog, Long> {
    List<SaleTransactionLog> findByTransaction_Sale_Id(Long saleId);
}
