package com.example.banking_system.repositories;

import com.example.banking_system.entities.Transaction;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByLimitExceeded(Boolean limit_exceeded);
}
