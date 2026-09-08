package com.plant.farmeraiassistent.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherClient {

    @Value("${weather.api.forecast.url}")
    private String forecastUrl;

    @Value("${weather.api.url}")
    private String currentWeatherUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public WeatherClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode getCurrentWeather(String city) {

        try {

            String url = currentWeatherUrl
                    + "?key=" + apiKey
                    + "&q=" + city + ",India"
                    + "&aqi=no";

            String response =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );

            return objectMapper.readTree(response);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to fetch weather data",
                    e
            );
        }
    }
    public JsonNode getForecast(String city, int days) {

        try {

            String url = forecastUrl
                    + "?key=" + apiKey
                    + "&q=" + city + ",India"
                    + "&days=" + days
                    + "&aqi=no"
                    + "&alerts=no";
//            System.out.println("WEATHER URL = " + url);

            String response =
                    restTemplate.getForObject(
                            url,
                            String.class
                    );

            return objectMapper.readTree(response);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to fetch weather forecast",
                    e
            );
        }
    }
}