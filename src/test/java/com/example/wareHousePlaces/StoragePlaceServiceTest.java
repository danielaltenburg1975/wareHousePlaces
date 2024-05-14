package com.example.wareHousePlaces;


import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.PalletRack_Data;
import com.example.wareHousePlaces.messageManager.MessageManager;
import com.example.wareHousePlaces.repository.ABC_Criteria_Repository;
import com.example.wareHousePlaces.repository.Article_Repository;
import com.example.wareHousePlaces.repository.PalletRack_Repository;
import com.example.wareHousePlaces.repository.StorageProperty_Repository;
import com.example.wareHousePlaces.services.StoragePlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StoragePlaceServiceTest {

    private StoragePlaceService storagePlaceService;

    @Mock
    private PalletRack_Repository palletRackRepository;

    @Mock
    private ABC_Criteria_Repository abcCriteriaRepository;

    @Mock
    private StorageProperty_Repository storagePropertyRepository;

    @Mock
    private Article_Repository articleRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        storagePlaceService = new StoragePlaceService(
                palletRackRepository,
                abcCriteriaRepository,
                storagePropertyRepository,
                articleRepository
        );
        MessageManager.loadMessages(Locale.getDefault());// wichtig sonst gibt es eine Nullpointerexception!
    }

    @Test
    void testSearchBestPlace() {
        // Mocking data
        double totalWeight = 100.0;
        int articleNumber = 1234;
        int height = 50;
        List<PalletRack_Data> placeList = new ArrayList<>();
        PalletRack_Data palletRackData = new PalletRack_Data();
        palletRackData.setMaxWeight(150);
        palletRackData.setMaxHeight(100);
        palletRackData.setAbc_Criteria("A");
        placeList.add(palletRackData);
        when(palletRackRepository.findAllFreePlaces()).thenReturn(placeList);

        Article_Data articleData = new Article_Data();
        articleData.setAbc_Criteria("A");
        articleData.setStorageProperty("XYZ");
        when(articleRepository.findByArticleNumber(articleNumber)).thenReturn(articleData);

        // Test method
        storagePlaceService.searchBestPlace(totalWeight, articleNumber, height);

        // Assertions
        assertEquals(placeList, storagePlaceService.resultList);
        assertEquals("Ideal.", storagePlaceService.getPlaceMessage());

    }

}

