package com.example.wareHousePlaces.restController;




import com.example.wareHousePlaces.data.*;
import com.example.wareHousePlaces.repository.*;
import com.example.wareHousePlaces.request.GetFreePlaceRequest;
import com.example.wareHousePlaces.request.PlaceSearchRequest;
import com.example.wareHousePlaces.request.SearchResultRequest;
import com.example.wareHousePlaces.services.StoragePlaceService;
import com.example.wareHousePlaces.services.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * Controller class for receiving and listing new trips
 *
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-04-17
 */


@RestController
public class PalletRack_Controller {

    @Autowired
    private final PalletRack_Repository palletRackRepository;
    private final ABC_Criteria_Repository abcCriteriaRepository;
    private final StorageAids_Repository storageAidsRepository;
    private final StorageProperty_Repository storagePropertyRepository;
    private final Article_Repository articleRepository;


    public PalletRack_Controller(PalletRack_Repository palletRackRepository, ABC_Criteria_Repository abcCriteriaRepository,
                                 StorageAids_Repository storageAidsRepository, StorageProperty_Repository storagePropertyRepository,
                                 Article_Repository articleRepository) {

        this.palletRackRepository = palletRackRepository;
        this.abcCriteriaRepository = abcCriteriaRepository;
        this.storageAidsRepository = storageAidsRepository;
        this.storagePropertyRepository = storagePropertyRepository;
        this.articleRepository = articleRepository;

    }


    /**
     * Handles HTTP GET request to retrieve a list of New_TripRequest objects.
     *
     * @return ResponseEntity containing a list of New_TripRequest objects.
     */
    @PostMapping("/getFreePlace")
    public ResponseEntity<List<PalletRack_Data>> getFreePlace(@RequestBody GetFreePlaceRequest getFreePlaceRequest) {
        try {
            List<PalletRack_Data> freePlaces = palletRackRepository.findByCriteria(getFreePlaceRequest.getName(), getFreePlaceRequest.getMaxWeight(),
                    getFreePlaceRequest.getMaxHeight(), getFreePlaceRequest.getAbc_Criteria(),
                    getFreePlaceRequest.getStorageAids(), getFreePlaceRequest.getStorageProperty(),
                    getFreePlaceRequest.getFree());

            if(freePlaces.isEmpty()){
                return ResponseEntity.noContent().build();
            }else {
                return ResponseEntity.ok().body(freePlaces);
            }

        } catch (Exception ex) {
            // Log the error
            ex.printStackTrace();
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



    @PostMapping("/searchPlace")
    public ResponseEntity<SearchResultRequest> searchPlace(@RequestBody PlaceSearchRequest placeSearchRequest) {

        WeightService weightService = new WeightService(articleRepository, storageAidsRepository);
        weightService.setTotalWeight(placeSearchRequest.getArticleNumber(), placeSearchRequest.getQuantity(),
                placeSearchRequest.getStorageAids());

        StoragePlaceService storagePlaceService = new StoragePlaceService(palletRackRepository, abcCriteriaRepository,
                 storagePropertyRepository, articleRepository);
        storagePlaceService.searchBestPlace(weightService.getTotalWeight(), placeSearchRequest.getArticleNumber(),
                                            placeSearchRequest.getHeight());

        PalletRack_Data bestPlaces = storagePlaceService.getBestPlace();
        String message = storagePlaceService.getPlaceMessage();

        SearchResultRequest searchResult = new SearchResultRequest(bestPlaces, message);


        return ResponseEntity.ok().body(searchResult);
    }


}
