package com.example.salessystem.dto;

import java.math.BigDecimal;

/**
 * Represents an individual transaction update when editing an existing sale.
 * Contains the ID of the sale transaction to update and the new quantity and
 * price values.  At least one of quantity or price should be provided.
 */
public class SaleTransactionUpdateRequest {
    private Long id;
    private Integer quantity;
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}