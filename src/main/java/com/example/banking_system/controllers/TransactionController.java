package com.example.banking_system.controllers;

import com.example.banking_system.dto.CurrencyDto;
import com.example.banking_system.dto.TransactionDto;
import com.example.banking_system.entities.*;
import com.example.banking_system.services.TransactionService;
import com.example.banking_system.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @PostMapping("/add")
    public ResponseEntity<Transaction> addTransaction(
            @RequestParam BigDecimal amount,
            @RequestParam String currencyName,
            @RequestParam Type type) {

        Currency currency = currencyRepository.findByName(currencyName)
                .orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        Transaction transaction = transactionService.addTransaction(amount, currency, type);

        if (transaction.getStatus() == Status.CANCEL) {
            return ResponseEntity.badRequest().body(transaction);
        }

        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/get")
    public ResponseEntity<List<TransactionDto>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        List<TransactionDto> transactionDTOs = transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getType(),
                        transaction.getLimitExceeded(),
                        transaction.getTransactionDate(),
                        new CurrencyDto(transaction.getCurrency().getId(), transaction.getCurrency().getName())
        ))
                .toList();
        return ResponseEntity.ok(transactionDTOs);
    }

    @GetMapping("/get")
    public ResponseEntity <List<TransactionDto>> getLimitExceededTransactions(
            @PathVariable Boolean limit_exceeded) {
        List<Transaction> transactions = transactionService.getLimitExceeded(limit_exceeded);
        List<TransactionDto> transactionDTOs = transactions.stream()
                .map(transaction -> new TransactionDto(
                        transaction.getId(),
                        transaction.getAmount(),
                        transaction.getStatus(),
                        transaction.getType(),
                        transaction.getLimitExceeded(),
                        transaction.getTransactionDate(),
                        new CurrencyDto(transaction.getCurrency().getId(), transaction.getCurrency().getName())
                ))
                .toList();
        return ResponseEntity.ok(transactionDTOs);
    }
}
