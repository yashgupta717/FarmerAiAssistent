package com.plant.farmeraiassistent.dto;

import lombok.Data;

@Data
public class WeatherResponse {

    private String city;
    private String country;

    private double temperature;
    private double feelsLike;

    private int humidity;

    private double windSpeed;

    private String condition;

    private String icon;
}