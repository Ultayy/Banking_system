package com.example.banking_system.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
 @Entity
 @Data
 @Table(name = "exchangerate")
 public class ExchangeRate {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     @Column(name = "id", nullable = false)
     private Long id;
     @Column(name = "currencypair", nullable = false)
     private String currencyPair;
     @Column(name = "rate", nullable = false, precision = 8, scale = 2)
     private BigDecimal rate;
     @Column(name = "date", nullable = false)
     private Timestamp date;
    }
