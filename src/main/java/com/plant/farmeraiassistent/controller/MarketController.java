package com.plant.farmeraiassistent.controller;

import com.plant.farmeraiassistent.client.MarketClient;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/market")
@CrossOrigin(origins = "*")
public class MarketController {

    private final MarketClient marketClient;

    public MarketController(MarketClient marketClient) {
        this.marketClient = marketClient;
    }

    @GetMapping
    public String getMarketPrices() {
        return marketClient.getPrices();
    }
}