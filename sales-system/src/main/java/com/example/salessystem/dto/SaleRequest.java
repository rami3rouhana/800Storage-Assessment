package com.example.salessystem.dto;

import java.util.List;

/**
 * Request payload for creating a new sale.  Contains the ID of the client
 * purchasing, the seller handling the sale and a list of transaction
 * descriptors.
 */
public class SaleRequest {
    private Long clientId;
    private String seller;
    private List<SaleTransactionRequest> transactions;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public List<SaleTransactionRequest> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<SaleTransactionRequest> transactions) {
        this.transactions = transactions;
    }
}