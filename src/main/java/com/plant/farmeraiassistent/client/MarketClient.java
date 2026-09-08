package com.plant.farmeraiassistent.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class MarketClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private final String apiUrl =
            "https://farmer.in/api/open/prices.json";

    public String getPrices() {
        return restTemplate.getForObject(apiUrl, String.class);
    }
}