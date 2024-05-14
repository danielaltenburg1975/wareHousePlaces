package com.example.wareHousePlaces.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name = "storageProperty")
public class StorageProperty_Data extends AbstractEntity{

    @NotBlank
    String name;

    String alternative;
}
