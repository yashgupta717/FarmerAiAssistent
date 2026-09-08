package com.plant.farmeraiassistent.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GeminiClient {

    @Value("${gemini.api.url}")
    private String apiUrl;

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GeminiClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode generateResponse(String prompt) {

        try {

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("model", "gemini-3.7-flash");
            requestBody.put("input", prompt);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", apiKey);

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            apiUrl,
                            request,
                            String.class
                    );

            return objectMapper.readTree(
                    response.getBody()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to communicate with Gemini API",
                    e
            );
        }
    }
}