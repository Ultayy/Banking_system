package com.example.banking_system.dto;

import com.example.banking_system.entities.Status;
import com.example.banking_system.entities.Type;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO for {@link com.example.banking_system.entities.Transaction}
 */
@Value
public class TransactionDto implements Serializable {
    Long id;
    BigDecimal amount;
    Status status;
    Type type;
    Boolean limitExceeded;
    Instant transactionDate;
    CurrencyDto currency;

//    public TransactionDto(Long id, BigDecimal amount, Status status, Type type, Boolean limitExceeded, Instant transactionDate, CurrencyDto currency) {
//        this.id = id;
//        this.amount = amount;
//        this.status = status;
//        this.type = type;
//        this.limitExceeded = limitExceeded;
//        this.transactionDate = transactionDate;
//        this.currency = currency;
//    }
}