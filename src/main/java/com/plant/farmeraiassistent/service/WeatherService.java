package com.plant.farmeraiassistent.service;

import com.plant.farmeraiassistent.dto.WeatherForecast;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.plant.farmeraiassistent.client.WeatherClient;
import com.plant.farmeraiassistent.dto.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;

    public WeatherService(WeatherClient weatherClient) {
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getWeather(String city) {

        JsonNode data =
                weatherClient.getCurrentWeather(city);

        WeatherResponse response =
                new WeatherResponse();

        // Location
        response.setCity(
                data.path("location")
                        .path("name")
                        .asText("Unknown")
        );

        response.setCountry(
                data.path("location")
                        .path("country")
                        .asText("Unknown")
        );

        // Current weather
        JsonNode current =
                data.path("current");

        response.setTemperature(
                current.path("temp_c")
                        .asDouble()
        );

        response.setFeelsLike(
                current.path("feelslike_c")
                        .asDouble()
        );

        response.setHumidity(
                current.path("humidity")
                        .asInt()
        );

        response.setWindSpeed(
                current.path("wind_kph")
                        .asDouble()
        );

        response.setCondition(
                current.path("condition")
                        .path("text")
                        .asText("Unknown")
        );

        response.setIcon(
                current.path("condition")
                        .path("icon")
                        .asText("")
        );

        return response;
    }
    public List<WeatherForecast> getForecast(String city, int days) {

        JsonNode data =
                weatherClient.getForecast(city, days);

        List<WeatherForecast> forecasts =
                new ArrayList<>();

        JsonNode forecastDays =
                data.path("forecast")
                        .path("forecastday");

        for (JsonNode day : forecastDays) {

            JsonNode dayData =
                    day.path("day");

            WeatherForecast forecast =
                    new WeatherForecast();

            forecast.setDate(
                    day.path("date")
                            .asText()
            );

            forecast.setMaxTemperature(
                    dayData.path("maxtemp_c")
                            .asDouble()
            );

            forecast.setMinTemperature(
                    dayData.path("mintemp_c")
                            .asDouble()
            );

            forecast.setChanceOfRain(
                    dayData.path("daily_chance_of_rain")
                            .asDouble()
            );

            forecast.setCondition(
                    dayData.path("condition")
                            .path("text")
                            .asText("Unknown")
            );

            forecast.setIcon(
                    dayData.path("condition")
                            .path("icon")
                            .asText("")
            );

            forecasts.add(forecast);
        }

        return forecasts;
    }
}