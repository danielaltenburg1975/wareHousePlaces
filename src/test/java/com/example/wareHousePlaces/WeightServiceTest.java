package com.example.wareHousePlaces;

import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.StorageAids_Data;
import com.example.wareHousePlaces.repository.Article_Repository;
import com.example.wareHousePlaces.repository.StorageAids_Repository;
import com.example.wareHousePlaces.services.WeightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WeightServiceTest {


    @Mock
    private Article_Repository articleRepository;

    @Mock
    private StorageAids_Repository storageAidsRepository;

    @InjectMocks
    private WeightService weightService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetTotalWeight() {
        // Mock data for article and storage aids
        Article_Data articleData = new Article_Data();
        articleData.setSingleWeight(2.5); // Set single weight to 2.5
        when(articleRepository.findByArticleNumber(123)).thenReturn(articleData);

        StorageAids_Data storageAidsData = new StorageAids_Data();
        storageAidsData.setWeightTara("15.0"); // Set storage aid weight to 1.0
        when(storageAidsRepository.findByName("EP")).thenReturn(storageAidsData);

        // Call the method to set total weight
        weightService.setTotalWeight(123, 5, "EP");

        // Check if total weight is calculated correctly
        assertEquals(2.5 * 5 + 15.0, weightService.getTotalWeight());
    }
}
