package com.example.banking_system.services;

import com.example.banking_system.entities.Currency;
import com.example.banking_system.entities.Limit;
import com.example.banking_system.entities.Type;
import com.example.banking_system.repositories.CurrencyRepository;
import com.example.banking_system.repositories.LimitRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class LimitService {

    private final LimitRepository limitRepository;
    private final CurrencyRepository currencyRepository;

    public LimitService(LimitRepository limitRepository, CurrencyRepository currencyRepository) {
        this.limitRepository = limitRepository;
        this.currencyRepository = currencyRepository;
    }


    @Scheduled(cron = "0 0 0 1 * ?")
    public void resetMonthlyLimits() {
        List<Limit> limits = limitRepository.findAll();

        for (Limit limit : limits) {
            if (limit.getType() == Type.PRODUCT) {
                resetLimit(limit);
            }
        }

        for (Limit limit : limits) {
            if (limit.getType() == Type.SERVICE) {
                resetLimit(limit);
            }
        }
    }
    private void resetLimit(Limit limit) {
        limit.setSpentAmount(BigDecimal.ZERO);
        limit.setCurrentLimit(limit.getDefaultLimit());
        limit.setSetDate(new Timestamp(System.currentTimeMillis()));
        limitRepository.save(limit);
    }


    public Limit setLimit(Currency currency, BigDecimal newLimit, Type type) {
        Limit existingLimit = limitRepository.findByType(type);

        Limit limit = new Limit();
        limit.setCurrentLimit(newLimit);
        limit.setDefaultLimit(newLimit);
        limit.setSetDate(new Timestamp(System.currentTimeMillis()));
        limit.setType(type);
        limit.setCurrency(currency);
        return limitRepository.save(limit);
    }
    public List<Limit> getAllLimits() {
        return limitRepository.findAll();
    }

    public Limit getLimitByType(Type type){
        List<Limit> limits = limitRepository.findByTypeOrderBySetDateDesc(type);

        if (limits.isEmpty()) {
            throw new IllegalStateException("No limits found for type: " + type);
        }

        return limits.get(0);
    }

    public void deleteLimitById(Long id) {
        limitRepository.deleteById(id);
    }

}
