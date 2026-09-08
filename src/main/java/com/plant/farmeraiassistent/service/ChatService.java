package com.plant.farmeraiassistent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.plant.farmeraiassistent.client.GeminiClient;
import com.plant.farmeraiassistent.dto.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final GeminiClient geminiClient;

    public ChatService(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    public ChatResponse chat(String message) {

        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException(
                    "Message cannot be empty."
            );
        }

        JsonNode response =
                geminiClient.generateResponse(message);

        ChatResponse chatResponse =
                new ChatResponse();

        chatResponse.setResponse(
                extractText(response)
        );

        return chatResponse;
    }

    private String extractText(JsonNode response) {

        JsonNode steps =
                response.path("steps");

        if (steps.isArray()) {

            for (JsonNode step : steps) {

                if ("model_output".equals(
                        step.path("type").asText())) {

                    JsonNode content =
                            step.path("content");

                    if (content.isArray()) {

                        for (JsonNode item : content) {

                            if ("text".equals(
                                    item.path("type").asText())) {

                                return item.path("text")
                                        .asText();
                            }
                        }
                    }
                }
            }
        }

        return "No response received from AI.";
    }
}