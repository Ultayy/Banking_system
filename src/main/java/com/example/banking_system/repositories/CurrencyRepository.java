package com.example.banking_system.repositories;

import com.example.banking_system.entities.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findById(Long currencyId);
    Optional<Currency> findByName(String name);
}
