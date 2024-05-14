package com.example.wareHousePlaces.data;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity(name="article")
public class Article_Data extends AbstractEntity{
    @NotBlank
    int articleNumber;
    @NotBlank
    String articleName;
    @NotBlank
    String unitOfMeasure;
    @NotBlank
    double singleWeight;
    @NotBlank
    String abc_Criteria;
    @NotBlank
    String storageProperty;
    @NotBlank
    int quantity;
    @NotBlank
    int minQuantity;
    @NotBlank
    String producer;
    @NotBlank
    String producerArticleNumber;

}
