package com.plant.farmeraiassistent.dto;

import lombok.Data;

@Data
public class WeatherForecast {

    private String date;

    private double maxTemperature;

    private double minTemperature;

    private double chanceOfRain;

    private String condition;

    private String icon;
}