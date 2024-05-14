package com.example.wareHousePlaces.data;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "abc_criteria")
public class ABC_Criteria_Data extends AbstractEntity{
    String name;
    String alternative;
}
