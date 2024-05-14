package com.example.wareHousePlaces;

import com.example.wareHousePlaces.data.PalletRack_Data;
import com.example.wareHousePlaces.repository.PalletRack_Repository;
import com.example.wareHousePlaces.request.GetFreePlaceRequest;
import com.example.wareHousePlaces.restController.PalletRack_Controller;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class PalletRack_Controller_getFreePlaces_Test {
    @Mock
    private PalletRack_Repository palletRackRepository;


    @InjectMocks
    private PalletRack_Controller palletRackController;

    public PalletRack_Controller_getFreePlaces_Test() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFreePlace_Success() {
        // Arrange
        GetFreePlaceRequest request = new GetFreePlaceRequest();
        when(palletRackRepository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(new PalletRack_Data()));

        // Act
        ResponseEntity<List<PalletRack_Data>> response = palletRackController.getFreePlace(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetFreePlace_NoDataFound() {
        // Arrange
        GetFreePlaceRequest request = new GetFreePlaceRequest();
        when(palletRackRepository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<PalletRack_Data>> response = palletRackController.getFreePlace(request);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetFreePlace_InternalServerError() {
        // Arrange
        GetFreePlaceRequest request = new GetFreePlaceRequest();
        when(palletRackRepository.findByCriteria(any(), any(), any(), any(), any(), any(), any()))
                .thenThrow(new RuntimeException("Database connection failed"));

        // Act
        ResponseEntity<List<PalletRack_Data>> response = palletRackController.getFreePlace(request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }

}
