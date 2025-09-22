package com.example.salessystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    private String seller;
    private BigDecimal total;

    @JsonManagedReference
    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SaleTransaction> transactions = new ArrayList<>();

    public Sale() {}

    public Sale(Client client, String seller) {
        this.client = client;
        this.seller = seller;
        this.creationDate = LocalDateTime.now();
        this.total = BigDecimal.ZERO;
    }

    @PrePersist
    public void prePersist() {
        if (creationDate == null) creationDate = LocalDateTime.now();
    }

    public void calculateTotal() {
        BigDecimal sum = BigDecimal.ZERO;
        for (SaleTransaction t : transactions) {
            BigDecimal lineTotal = t.getPrice().multiply(new BigDecimal(t.getQuantity()));
            sum = sum.add(lineTotal);
        }
        this.total = sum;
    }

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public String getSeller() { return seller; }
    public void setSeller(String seller) { this.seller = seller; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<SaleTransaction> getTransactions() { return transactions; }
    public void setTransactions(List<SaleTransaction> transactions) { this.transactions = transactions; }
}
