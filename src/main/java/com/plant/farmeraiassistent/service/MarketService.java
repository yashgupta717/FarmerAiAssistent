package com.plant.farmeraiassistent.service;

import com.plant.farmeraiassistent.client.MarketClient;
import org.springframework.stereotype.Service;

@Service
public class MarketService {

    private final MarketClient marketClient;

    public MarketService(MarketClient marketClient) {
        this.marketClient = marketClient;
    }
}