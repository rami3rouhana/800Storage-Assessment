package com.example.salessystem.service;

import com.example.salessystem.dto.SaleRequest;
import com.example.salessystem.dto.SaleTransactionRequest;
import com.example.salessystem.dto.SaleTransactionUpdateRequest;
import com.example.salessystem.dto.SaleUpdateRequest;
import com.example.salessystem.model.*;
import com.example.salessystem.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service layer for sales.  This service encapsulates the creation and
 * updating of sales and their transactions, including logging updates to
 * transaction details.
 */
@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleTransactionRepository saleTransactionRepository;
    private final SaleTransactionLogRepository saleTransactionLogRepository;

    public SaleService(SaleRepository saleRepository,
                       ClientRepository clientRepository,
                       ProductRepository productRepository,
                       SaleTransactionRepository saleTransactionRepository,
                       SaleTransactionLogRepository saleTransactionLogRepository) {
        this.saleRepository = saleRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.saleTransactionRepository = saleTransactionRepository;
        this.saleTransactionLogRepository = saleTransactionLogRepository;
    }

    /**
     * Retrieve all sales with their transactions eagerly loaded.
     */
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    /**
     * Create a new sale.  Transactions are persisted together with the sale via
     * cascading.  Throws 404 if the client or any referenced product cannot be
     * found.
     */
    @Transactional
    public Sale createSale(SaleRequest request) {
        // Look up the client making the purchase
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));

        // Instantiate the sale
        Sale sale = new Sale(client, request.getSeller());

        // For each transaction descriptor create an entity and attach it to the sale
        if (request.getTransactions() == null || request.getTransactions().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sale must contain at least one transaction");
        }

        for (SaleTransactionRequest t : request.getTransactions()) {
            Product product = productRepository.findById(t.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
            if (t.getQuantity() == null || t.getQuantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
            }
            if (t.getPrice() == null || t.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be positive");
            }
            SaleTransaction transaction = new SaleTransaction(sale, product, t.getQuantity(), t.getPrice());
            sale.getTransactions().add(transaction);
        }

        // Compute total and persist
        sale.calculateTotal();
        return saleRepository.save(sale);
    }

    /**
     * Update an existing sale's transaction quantities and prices.  Logs any
     * changes to a separate table.  Throws 404 if the sale or any referenced
     * transaction cannot be found.  Returns the updated sale.
     */
    @Transactional
    public Sale updateSale(Long saleId, SaleUpdateRequest request) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        if (request.getTransactions() == null || request.getTransactions().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No transactions specified for update");
        }

        // Create logs and update transactions
        for (SaleTransactionUpdateRequest tr : request.getTransactions()) {
            SaleTransaction transaction = saleTransactionRepository.findById(tr.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));
            // Ensure that the transaction belongs to this sale
            if (!transaction.getSale().getId().equals(saleId)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transaction does not belong to sale");
            }
            // Record old values
            Integer oldQty = transaction.getQuantity();
            BigDecimal oldPrice = transaction.getPrice();
            boolean changed = false;

            // Apply updates
            if (tr.getQuantity() != null && !tr.getQuantity().equals(oldQty)) {
                if (tr.getQuantity() <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be positive");
                }
                transaction.setQuantity(tr.getQuantity());
                changed = true;
            }
            if (tr.getPrice() != null && oldPrice.compareTo(tr.getPrice()) != 0) {
                if (tr.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price must be positive");
                }
                transaction.setPrice(tr.getPrice());
                changed = true;
            }
            // If there was a change, create a log entry
            if (changed) {
                SaleTransactionLog log = new SaleTransactionLog(transaction, oldQty, transaction.getQuantity(), oldPrice, transaction.getPrice());
                saleTransactionLogRepository.save(log);
            }
        }

        // After updates recalculate the sale total
        sale.calculateTotal();
        return saleRepository.save(sale);
    }
}