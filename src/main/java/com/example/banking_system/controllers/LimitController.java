package com.example.banking_system.controllers;

import com.example.banking_system.dto.CurrencyDto;
import com.example.banking_system.dto.LimitDto;
import com.example.banking_system.entities.Currency;
import com.example.banking_system.entities.Limit;
import com.example.banking_system.entities.Type;
import com.example.banking_system.repositories.CurrencyRepository;
import com.example.banking_system.services.LimitService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/limits")
public class LimitController {

    private final LimitService limitService;
    private final CurrencyRepository currencyRepository;

    @Autowired
    public LimitController(LimitService limitService, CurrencyRepository currencyRepository) {
        this.limitService = limitService;
        this.currencyRepository = currencyRepository;
    }

    @PostMapping("/set")
    public ResponseEntity<Limit> setLimit(
            @RequestParam String currencyName,
            @RequestParam BigDecimal limitSum,
            @RequestParam String type) {

        Currency currency = currencyRepository.findByName(currencyName)
                .orElseThrow(() -> new IllegalArgumentException("Invalid currency ID"));

        Type enumType = Type.valueOf(type.toUpperCase()); // Преобразование строки в Enum

        Limit limit = limitService.setLimit(currency, limitSum, enumType);
        return ResponseEntity.ok(limit);
    }

    @GetMapping("/get")
    public ResponseEntity<List<LimitDto>> getAllLimits() {
        List<Limit> limits = limitService.getAllLimits();
        List<LimitDto> limitDTOs = limits.stream()
                .map(limit -> new LimitDto(
                        limit.getId(),
                        limit.getCurrentLimit(),
                        limit.getDefaultLimit(),
                        limit.getSpentAmount(),
                        limit.getSetDate(),
                        limit.getType(),
                        new CurrencyDto(limit.getCurrency().getId(), limit.getCurrency().getName())
                ))
                .toList();

        return ResponseEntity.ok(limitDTOs);
    }
    @GetMapping("/{type}")
    public ResponseEntity<LimitDto> getLimitsByType(@PathVariable String type) {
        Type enumType = Type.valueOf(type.toUpperCase());
        Limit limit = limitService.getLimitByType(enumType);
        LimitDto LimitDTO =  new LimitDto(
                        limit.getId(),
                        limit.getCurrentLimit(),
                        limit.getDefaultLimit(),
                        limit.getSpentAmount(),
                        limit.getSetDate(),
                        limit.getType(),
                        new CurrencyDto(limit.getCurrency().getId(), limit.getCurrency().getName())
                );
        return ResponseEntity.ok(LimitDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLimit(@PathVariable Long id) {
        limitService.deleteLimitById(id);
        return ResponseEntity.noContent().build();
    }


}
