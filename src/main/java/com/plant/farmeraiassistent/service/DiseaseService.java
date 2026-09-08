package com.plant.farmeraiassistent.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.plant.farmeraiassistent.client.KindwiseClient;
import com.plant.farmeraiassistent.dto.DiseaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiseaseService {

    private final KindwiseClient kindwiseClient;

    public DiseaseService(KindwiseClient kindwiseClient) {
        this.kindwiseClient = kindwiseClient;
    }

    public DiseaseResponse detectDisease(MultipartFile image)
            throws IOException {

        // Validate image
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException(
                    "Please upload an image."
            );
        }

        // Send image to Kindwise
        JsonNode response =
                kindwiseClient.identifyPlant(image.getBytes());

        // Root result
        JsonNode result =
                response.get("result");

        if (result == null) {
            throw new RuntimeException(
                    "Invalid response from disease detection API"
            );
        }

        // ==============================
        // CROP
        // ==============================

        JsonNode cropSuggestions =
                result.path("crop")
                        .path("suggestions");

        if (cropSuggestions.isEmpty()) {
            throw new RuntimeException(
                    "Could not identify the crop"
            );
        }

        JsonNode crop =
                cropSuggestions.get(0);

        // ==============================
        // DISEASE
        // ==============================

        JsonNode diseaseSuggestions =
                result.path("disease")
                        .path("suggestions");

        if (diseaseSuggestions.isEmpty()) {
            throw new RuntimeException(
                    "Could not identify the disease"
            );
        }

        JsonNode disease =
                diseaseSuggestions.get(0);

        JsonNode details =
                disease.path("details");

        JsonNode treatment =
                details.path("treatment");

        // ==============================
        // CREATE RESPONSE
        // ==============================

        DiseaseResponse responseDto =
                new DiseaseResponse();

        responseDto.setCrop(
                crop.path("name")
                        .asText("Unknown")
        );

        responseDto.setCropConfidence(
                crop.path("probability")
                        .asDouble() * 100
        );

        responseDto.setDisease(
                disease.path("name")
                        .asText("Unknown")
        );

        responseDto.setDiseaseConfidence(
                disease.path("probability")
                        .asDouble() * 100
        );

        responseDto.setScientificName(
                disease.path("scientific_name")
                        .asText("N/A")
        );

        responseDto.setDescription(
                details.path("description")
                        .asText("No description available.")
        );

        responseDto.setPrevention(
                convertToList(
                        treatment.path("prevention")
                )
        );

        responseDto.setChemicalTreatment(
                convertToList(
                        treatment.path("chemical treatment")
                )
        );

        responseDto.setBiologicalTreatment(
                convertToList(
                        treatment.path("biological treatment")
                )
        );

        return responseDto;
    }

    // ==============================
    // JSON ARRAY → JAVA LIST
    // ==============================

    private List<String> convertToList(JsonNode node) {

        List<String> list =
                new ArrayList<>();

        if (node.isArray()) {

            for (JsonNode item : node) {
                list.add(item.asText());
            }
        }

        return list;
    }
}