package com.example.wareHousePlaces.data;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;



@Data
@Entity(name = "palletrack")
public class PalletRack_Data extends AbstractEntity {

    @NotBlank
    String name;

    @NotBlank
    int maxWeight;

    @NotBlank
    int maxHeight;

    @NotBlank
    String abc_Criteria;

    @NotBlank
    String storageAids;

    @NotBlank
    String storageProperty;

    @NotBlank
    String free;

    public PalletRack_Data() {

    }
}
