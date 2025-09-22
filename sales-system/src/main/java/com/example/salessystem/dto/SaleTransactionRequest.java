package com.example.salessystem.dto;

import java.math.BigDecimal;

/**
 * Represents a single transaction within a sale creation request.  It specifies
 * which product is being sold, the quantity being purchased and the unit
 * price.  All fields are required when creating a new sale.
 */
public class SaleTransactionRequest {
    private Long productId;
    private Integer quantity;
    private BigDecimal price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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