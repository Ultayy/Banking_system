package com.example.banking_system.services;

import com.example.banking_system.entities.*;
import com.example.banking_system.repositories.CurrencyRepository;
import com.example.banking_system.repositories.LimitRepository;
import com.example.banking_system.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.banking_system.services.ExchangeRateService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private LimitService limitService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;
    @Autowired
    private LimitRepository limitRepository;


    @Transactional
    public Transaction addTransaction(BigDecimal amount, Currency currency, Type type) {

        BigDecimal amountInUSD = exchangeRateService.convertToUSD(amount, currency.getName());


        boolean limitExceeded = checkLimitExceeded(amountInUSD, type);


        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setType(type);
        transaction.setTransactionDate(Instant.now());
        transaction.setLimitExceeded(limitExceeded);

        if (limitExceeded) {
            transaction.setStatus(Status.CANCEL);
        } else {
            transaction.setStatus(Status.APPROVE);
        }

        return transactionRepository.save(transaction);

    }

    private boolean checkLimitExceeded(BigDecimal amountInUSD, Type type) {

        Limit limit = limitRepository.findByType(type);

        if (limit == null) {
            throw new IllegalStateException("Limit not found for type: " + type);
        }

        BigDecimal newSpentAmount = limit.getSpentAmount().add(amountInUSD);

        if (newSpentAmount.compareTo(limit.getCurrentLimit()) > 0){
            return true;
        };
        limit.setSpentAmount(newSpentAmount);
        limitRepository.save(limit);
        return false;
    }


    public List<Transaction> getAllTransactions() {
        return  transactionRepository.findAll();
    }

    public List<Transaction> getLimitExceeded(Boolean limit_exceeded){
        return transactionRepository.findByLimitExceeded(limit_exceeded);
    }
}
