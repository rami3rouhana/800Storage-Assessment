package com.example.salessystem.controller;

import com.example.salessystem.dto.SaleRequest;
import com.example.salessystem.dto.SaleUpdateRequest;
import com.example.salessystem.model.Sale;
import com.example.salessystem.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller exposing endpoints for managing sales.  Supports listing
 * existing sales, creating new sales with multiple transactions and
 * updating transaction details of an existing sale.  Logs are created
 * automatically when updating transactions.
 */
@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    /**
     * Retrieve all sales.  Sales include their transactions eagerly.
     */
    @GetMapping
    public List<Sale> getSales() {
        return saleService.findAll();
    }

    /**
     * Create a new sale with one or more transactions.
     */
    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody SaleRequest request) {
        Sale created = saleService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * Update quantities and prices of an existing sale.  Returns the updated
     * sale.  A 404 is returned if the sale or any transaction cannot be found.
     */
    @PutMapping("/{id}")
    public Sale updateSale(@PathVariable Long id, @RequestBody SaleUpdateRequest request) {
        return saleService.updateSale(id, request);
    }
}