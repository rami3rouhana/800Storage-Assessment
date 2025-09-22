package com.example.salessystem.controller;

import com.example.salessystem.model.SaleTransactionLog;
import com.example.salessystem.repository.SaleTransactionLogRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleLogController {

    private final SaleTransactionLogRepository logRepository;

    public SaleLogController(SaleTransactionLogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @GetMapping("/{id}/logs")
    public List<SaleTransactionLog> getSaleLogs(@PathVariable Long id) {
        return logRepository.findByTransaction_Sale_Id(id);
    }
}
