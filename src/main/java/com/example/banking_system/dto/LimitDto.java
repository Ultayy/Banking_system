package com.example.banking_system.dto;

import com.example.banking_system.entities.Type;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * DTO for {@link com.example.banking_system.entities.Limit}
 */
@Value
public class LimitDto implements Serializable {
    Long id;
    BigDecimal currentLimit;
    BigDecimal defaultLimit;
    BigDecimal spentAmount;
    Timestamp setDate;
    Type type;
    CurrencyDto currency;
}