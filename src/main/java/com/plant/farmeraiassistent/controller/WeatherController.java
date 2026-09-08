package com.plant.farmeraiassistent.controller;

import com.plant.farmeraiassistent.dto.WeatherForecast;
import com.plant.farmeraiassistent.dto.WeatherResponse;
import com.plant.farmeraiassistent.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResponse getWeather(
            @RequestParam String city) {

        return weatherService.getWeather(city);
    }

    @GetMapping("/forecast")
    public List<WeatherForecast> getForecast(
            @RequestParam String city,
            @RequestParam(defaultValue = "3") int days) {

        return weatherService.getForecast(city, days);
    }
}