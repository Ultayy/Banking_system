package com.example.banking_system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    private BigDecimal amount;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Column(name = "limit_exceeded", nullable = false)
    private Boolean limitExceeded = false;

    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public void setTypeToProduct() {
        this.type = Type.PRODUCT;
    }

    public void setTypeToService() {
        this.type = Type.SERVICE;
    }

    public void setSatatusToCancel(){
        this.status = Status.CANCEL;
    }

    public void setStatusToApprove(){
        this.status = Status.APPROVE;
    }

}