package com.example.salessystem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_transaction_logs")
public class SaleTransactionLog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_id")
    @JsonIgnoreProperties({"sale", "product"}) // prevent deep graphs
    private SaleTransaction transaction;

    @Column(name = "old_quantity")
    private Integer oldQuantity;
    @Column(name = "new_quantity")
    private Integer newQuantity;

    @Column(name = "old_price", precision = 38, scale = 2)
    private BigDecimal oldPrice;
    @Column(name = "new_price", precision = 38, scale = 2)
    private BigDecimal newPrice;

    public SaleTransactionLog() {}

    public SaleTransactionLog(SaleTransaction transaction, Integer oldQty, Integer newQty,
                              BigDecimal oldPrice, BigDecimal newPrice) {
        this.transaction = transaction;
        this.oldQuantity = oldQty;
        this.newQuantity = newQty;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.updateDate = LocalDateTime.now();
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getUpdateDate() { return updateDate; }
    public void setUpdateDate(LocalDateTime updateDate) { this.updateDate = updateDate; }

    public SaleTransaction getTransaction() { return transaction; }
    public void setTransaction(SaleTransaction transaction) { this.transaction = transaction; }

    public Integer getOldQuantity() { return oldQuantity; }
    public void setOldQuantity(Integer oldQuantity) { this.oldQuantity = oldQuantity; }

    public Integer getNewQuantity() { return newQuantity; }
    public void setNewQuantity(Integer newQuantity) { this.newQuantity = newQuantity; }

    public BigDecimal getOldPrice() { return oldPrice; }
    public void setOldPrice(BigDecimal oldPrice) { this.oldPrice = oldPrice; }

    public BigDecimal getNewPrice() { return newPrice; }
    public void setNewPrice(BigDecimal newPrice) { this.newPrice = newPrice; }
}
