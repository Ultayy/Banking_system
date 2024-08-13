package com.example.banking_system.repositories;

import com.example.banking_system.entities.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findByCurrencyPair(String currencyPair);
}
