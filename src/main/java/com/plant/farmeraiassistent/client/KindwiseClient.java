package com.plant.farmeraiassistent.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class KindwiseClient {

    @Value("${disease.api.url}")
    private String apiUrl;

    @Value("${disease.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public KindwiseClient() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public JsonNode identifyPlant(byte[] imageBytes) {

        try {

            String base64Image =
                    Base64.getEncoder()
                            .encodeToString(imageBytes);

            Map<String, Object> requestBody =
                    new HashMap<>();

            requestBody.put(
                    "images",
                    List.of(base64Image)
            );

            HttpHeaders headers =
                    new HttpHeaders();

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            headers.set(
                    "Api-Key",
                    apiKey
            );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(
                            requestBody,
                            headers
                    );

            String url = apiUrl
                    + "?details=treatment,description,taxonomy"
                    + "&language=en";

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            request,
                            String.class
                    );

            return objectMapper.readTree(
                    response.getBody()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Kindwise identification error: "
                            + e.getMessage(),
                    e
            );
        }
    }

    public JsonNode retrieveIdentification(
            String accessToken) {

        try {

            String retrieveUrl =
                    apiUrl + "/" + accessToken;

            HttpHeaders headers =
                    new HttpHeaders();

            headers.set(
                    "Api-Key",
                    apiKey
            );

            HttpEntity<Void> request =
                    new HttpEntity<>(headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(
                            retrieveUrl,
                            HttpMethod.GET,
                            request,
                            String.class
                    );

            return objectMapper.readTree(
                    response.getBody()
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    "Kindwise retrieval error: "
                            + e.getMessage(),
                    e
            );
        }
    }
}