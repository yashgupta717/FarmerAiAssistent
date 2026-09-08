package com.plant.farmeraiassistent.dto;

import lombok.Data;

@Data
public class MarketResponse {

    private String commodity;

    private String market;

    private String state;

    private String district;

    private double minPrice;

    private double maxPrice;

    private double modalPrice;

    private String date;
}