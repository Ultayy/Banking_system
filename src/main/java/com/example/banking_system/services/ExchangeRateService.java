package com.example.banking_system.services;

import com.example.banking_system.entities.ExchangeRate;
import com.example.banking_system.repositories.ExchangeRateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExchangeRateService {

    @Value("${twelvedata.apikey}")
    private String apiKey;
    @Value("${twelvedata.apiurl}")
    private String API_URL;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public Map<String, Object> getExchangeRates(String currencyPair) {
        RestTemplate restTemplate = new RestTemplate();


        Map<String, String> params = new HashMap<>();
        params.put("currencyPair", currencyPair);

        Map<String, Object> response = restTemplate.getForObject(API_URL, Map.class, params);

        if (response != null && response.containsKey("values")) {
            return convertTwerveData(response);

        }

        return new HashMap<>();
    }

    private Map<String, Object> convertTwerveData(Map<String, Object> data) {
         SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Map<String, Object>> rateList = (List<Map<String, Object>>) data.get("values");
        Map<String, Object> firstValue = rateList.get(0);

            BigDecimal rate = new BigDecimal ((String) firstValue.get("close"));
            String datetimeStr = (String) firstValue.get("datetime");

        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(datetimeStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Timestamp date = new Timestamp(parsedDate.getTime());
                Map<String, Object> resultMap = new HashMap<>();
                resultMap.put("rate", rate);
                resultMap.put("date", date);

        return resultMap;
    }

    public BigDecimal convertToUSD(BigDecimal amount, String currencyName) {
        String currencyPair = currencyName + "/USD";

        Map<String, Object> exchangeRateData = getExchangeRates(currencyPair);

        BigDecimal rate = (BigDecimal) exchangeRateData.get("rate");

        if (rate == null) {
            throw new RuntimeException("Exchange rate for " + currencyName + " to USD not found.");
        }

        return amount.multiply(rate);
    }


}
