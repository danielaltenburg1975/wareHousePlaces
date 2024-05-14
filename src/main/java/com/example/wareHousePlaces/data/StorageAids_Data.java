package com.example.wareHousePlaces.data;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
@Entity(name = "storageAids")
public class StorageAids_Data extends AbstractEntity{

    @NotBlank
    String name;

    String alternative;

    @NotBlank
    String weightTara;

    String size_bxl;
}
