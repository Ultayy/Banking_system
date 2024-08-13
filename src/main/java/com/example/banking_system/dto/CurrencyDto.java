package com.example.banking_system.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.banking_system.entities.Currency}
 */
@Value
public class CurrencyDto implements Serializable {
    Long id;
    String name;
}