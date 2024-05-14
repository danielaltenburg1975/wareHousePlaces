package com.example.wareHousePlaces.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetFreePlaceRequest {
    String abc_Criteria;
    String storageAids;
    String storageProperty;
    int maxWeight;
    String name;
    String maxHeight;
    String free;
}
