package com.plant.farmeraiassistent.dto;



import lombok.Data;

import java.util.List;

@Data
public class DiseaseResponse {

    private String crop;
    private double cropConfidence;

    private String disease;
    private double diseaseConfidence;

    private String scientificName;

    private String description;

    private List<String> prevention;

    private List<String> chemicalTreatment;

    private List<String> biologicalTreatment;
}
