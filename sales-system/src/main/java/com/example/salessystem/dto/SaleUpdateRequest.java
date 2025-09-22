package com.example.salessystem.dto;

import java.util.List;

/**
 * Request payload for editing an existing sale.  Contains a list of
 * transaction updates.  Each transaction update refers to an existing
 * transaction belonging to the sale and provides new values for quantity
 * and/or price.
 */
public class SaleUpdateRequest {
    private List<SaleTransactionUpdateRequest> transactions;

    public List<SaleTransactionUpdateRequest> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SaleTransactionUpdateRequest> transactions) {
        this.transactions = transactions;
    }
}