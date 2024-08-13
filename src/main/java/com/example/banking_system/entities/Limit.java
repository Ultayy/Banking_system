package com.example.banking_system.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Data
@Getter
@Setter
@Entity
@Table(name = "limits")
public class Limit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "currentLimit", nullable = false, precision = 8, scale = 2)
    private BigDecimal currentLimit;

    @Column(name = "defaultLimit", nullable = false, precision = 8, scale = 2)
    private BigDecimal defaultLimit = BigDecimal.valueOf(1000.00);

    @Column(name = "spentAmount", nullable = false, precision = 8, scale = 2)
    private BigDecimal spentAmount = BigDecimal.ZERO;

    @Column(name = "set_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp setDate;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public void setTypeToProduct() {
        this.type = Type.PRODUCT;
    }

    public void setTypeToService() {
        this.type = Type.SERVICE;
    }
}