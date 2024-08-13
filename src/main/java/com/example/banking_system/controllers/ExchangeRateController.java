package com.example.banking_system.controllers;

import com.example.banking_system.entities.ExchangeRate;
import com.example.banking_system.repositories.ExchangeRateRepository;
import com.example.banking_system.repositories.LimitRepository;
import com.example.banking_system.services.ExchangeRateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateController(ExchangeRateService exchangeRateService,
                                  ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateService = exchangeRateService;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @GetMapping("/update")
    public ResponseEntity<ExchangeRate> updateExchangeRates(@RequestParam String currencyPair) {
        Map<String, Object> exchangeRateData = exchangeRateService.getExchangeRates(currencyPair);
        System.out.println(exchangeRateData);
            BigDecimal rate = (BigDecimal) exchangeRateData.get("rate");
            Timestamp date = (Timestamp) exchangeRateData.get("date");
            ExchangeRate exchangeRate = new ExchangeRate();
            exchangeRate.setRate(rate);
            exchangeRate.setCurrencyPair(currencyPair);
            exchangeRate.setDate(date);
            exchangeRateRepository.save(exchangeRate);
        return ResponseEntity.ok(exchangeRate);
    }

}
