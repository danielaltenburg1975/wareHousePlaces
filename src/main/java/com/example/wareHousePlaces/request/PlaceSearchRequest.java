package com.example.wareHousePlaces.request;

import lombok.Data;

@Data
public class PlaceSearchRequest {
    private int articleNumber;
    private int quantity;
    private int height;
    private String storageAids;


}
