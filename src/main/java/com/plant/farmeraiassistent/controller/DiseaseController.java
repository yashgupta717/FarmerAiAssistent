package com.plant.farmeraiassistent.controller;

import com.plant.farmeraiassistent.dto.DiseaseResponse;
import com.plant.farmeraiassistent.service.DiseaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/disease")
@CrossOrigin(origins = "*")
public class DiseaseController {

    private final DiseaseService diseaseService;

    public DiseaseController(DiseaseService diseaseService) {
        this.diseaseService = diseaseService;
    }

    @PostMapping("/detect")
    public ResponseEntity<?> detectDisease(
            @RequestParam("image") MultipartFile image) {

        try {

            DiseaseResponse result =
                    diseaseService.detectDisease(image);

            return ResponseEntity.ok(result);

        } catch (Exception e) {

            return ResponseEntity
                    .internalServerError()
                    .body(e.getMessage());
        }
    }
}