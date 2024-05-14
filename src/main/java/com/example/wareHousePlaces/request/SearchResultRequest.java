package com.example.wareHousePlaces.request;

import com.example.wareHousePlaces.data.PalletRack_Data;
import lombok.Data;

import java.util.List;
import java.util.Locale;

@Data
public class SearchResultRequest {
    private PalletRack_Data bestPlaces;
    private String message;


    // Constructor
    public SearchResultRequest(PalletRack_Data bestPlaces, String message) {
        this.bestPlaces = bestPlaces;
        this.message = message;

    }
}
