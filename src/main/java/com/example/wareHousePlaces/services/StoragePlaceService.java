package com.example.wareHousePlaces.services;
import com.example.wareHousePlaces.data.ABC_Criteria_Data;
import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.PalletRack_Data;
import com.example.wareHousePlaces.data.StorageProperty_Data;
import com.example.wareHousePlaces.messageManager.MessageManager;
import com.example.wareHousePlaces.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

 /**
 * @author Daniel Altenburg
 * @version 1.0
 * @since 2024-05-13

 * The StoragePlaceService provides functionalities for managing storage places in a warehouse.
 * It allows searching for the best storage place for a specific product based on various criteria.
 */
public class StoragePlaceService {
    /* Attributes for storing data and intermediate states during processing.

    A list is created here for each search steps,
    to obtain a suitable entry point for alternative
    searches.*/

    public List<PalletRack_Data> resultList;
    public List<PalletRack_Data> placeList;
    public List<PalletRack_Data> weightList;
    public List<PalletRack_Data> heightList;
    public List<PalletRack_Data> abcCritteriaList;
    public List<PalletRack_Data> propertyCritteriaList;

    private boolean stopSearch;
    private int assessmentPoints = 0;
    private int roundedValue, height;
    private String message = null;

    private final ABC_Criteria_Repository abcCriteriaRepository;
    private final StorageProperty_Repository storagePropertyRepository;
    private final Article_Repository articleRepository;
    private final PalletRack_Repository palletRackRepository;

    public StoragePlaceService(PalletRack_Repository palletRackRepository, ABC_Criteria_Repository abcCriteriaRepository,
                               StorageProperty_Repository storagePropertyRepository,
                               Article_Repository articleRepository){
        this.abcCriteriaRepository = abcCriteriaRepository;
        this.storagePropertyRepository = storagePropertyRepository;
        this.articleRepository = articleRepository;
        this.palletRackRepository = palletRackRepository;
        this.resultList = new ArrayList<>();
    }



     /**
      * Searches for the best storage place for a product based on weight, height, and other criteria.
      *
      * @param totalWeight   The total weight of the product.
      * @param articleNumber The article number of the product.
      * @param height        The height of the product.
      */
     public void searchBestPlace(double totalWeight, int articleNumber, int height){
         try {
             // Attempt to retrieve all free places from the pallet rack repository
             placeList = palletRackRepository.findAllFreePlaces(); // Save all free spaces in a list
         } catch (Exception e) {
             // If an exception occurs (e.g., database connection error)
             // Set an appropriate error message and log the exception
             message = MessageManager.getMessage("noDatabaseConnection");
             e.printStackTrace();
         }

         // Round up the total weight if it's not an integer
         roundedValue = roundUpIfNotInteger(totalWeight);
         this.height = height;
         // Retrieve article data based on the article number
         Article_Data article = articleRepository.findByArticleNumber(articleNumber);

         // Check if there are any available places
         if (!placeList.isEmpty()) {
             // If there are available places, check weight opportunities
             checkWeightOpportunities();
         } else {
             // If no available places were found, set an appropriate message
             message = MessageManager.getMessage("noFreePlaceFound");
         }

         // If weight opportunities were not found, check height opportunities
         if (!checkWeightOpportunities()) {
             checkHeightOpportunities();

             // If height opportunities were not found, check ABC opportunities
             if (!checkHeightOpportunities()) {
                 checkABC_Opportunities(article.getAbc_Criteria());

                 // If ABC opportunities were not found, check property opportunities
                 if (!stopSearch) {
                     checkPropertyOpportunities(article.getStorageProperty());
                 }
             }
         }

         // If no suitable place was found, set an appropriate message
         if (stopSearch) {
             message = MessageManager.getMessage("noSuitablePlaceFound");
         }

         // Retrieve assessment message
         getAssessmentMessage();
         // Set the result list to the ABC criteria list
         resultList = abcCritteriaList;
     }


     // Return the best place or null.
    public PalletRack_Data getBestPlace(){
        return resultList.isEmpty() ? null : resultList.get(0);
    }
    // Return a current message
    public String getPlaceMessage(){
        return getAssessmentMessage();
    }

    // Check if there are places for the weight.
    public boolean checkWeightOpportunities(){

        weightList = placeList.stream()
                .filter(rackData -> rackData.getMaxWeight() >= roundedValue)
                .collect(Collectors.toList());
        return weightList.isEmpty();
    }

     // Check if there are places for the height.
    public boolean checkHeightOpportunities(){
        heightList = weightList.stream()
                .filter(rackData -> rackData.getMaxHeight()>= height)
                .collect(Collectors.toList());
        return heightList.isEmpty();
    }

     /**
      * Checks for opportunities based on ABC criteria.
      *
      * @param tempABC_Criteria The ABC criteria to check.
      * @return True if opportunities were found, false otherwise.
      */
     public boolean checkABC_Opportunities(String tempABC_Criteria) {
         // Filter the heightList to find racks matching the given ABC criteria
         abcCritteriaList = heightList.stream()
                 .filter(rackData -> rackData.getAbc_Criteria().equals(tempABC_Criteria))
                 .collect(Collectors.toList());

         // If no racks match the given ABC criteria
         if (abcCritteriaList.isEmpty()) {
             // Try to find an alternative ABC criteria from the repository
             ABC_Criteria_Data abcCriteriaData = abcCriteriaRepository.findByName(tempABC_Criteria);
             // If an alternative was found
             if (abcCriteriaData != null && abcCriteriaData.getAlternative() != null) {
                 // Update the current ABC criteria with the alternative
                 String currentABC_Criteria = abcCriteriaData.getAlternative();
                 // Increase the assessment points by 2 for finding an alternative
                 assessmentPoints += 2;
                 // Recursively check opportunities with the alternative ABC criteria
                 return checkABC_Opportunities(currentABC_Criteria);
             }
         }
         // Set stopSearch to true if no opportunities were found
         stopSearch = abcCritteriaList.isEmpty();
         return stopSearch;
     }

     /**
      * Checks for opportunities based on storage property criteria.
      *
      * @param tempProperty_Criteria The storage property criteria to check.
      * @return True if opportunities were found, false otherwise.
      */
     public boolean checkPropertyOpportunities(String tempProperty_Criteria) {
         // Filter the abcCritteriaList to find racks matching the given storage property criteria
         propertyCritteriaList = abcCritteriaList.stream()
                 .filter(rackData -> rackData.getAbc_Criteria().equals(tempProperty_Criteria))
                 .collect(Collectors.toList());

         // If no racks match the given storage property criteria
         if (propertyCritteriaList.isEmpty()) {
             // Try to find an alternative storage property criteria from the repository
             StorageProperty_Data storagePropertyData = storagePropertyRepository.findByName(tempProperty_Criteria);
             // If an alternative was found
             if (storagePropertyData != null && storagePropertyData.getAlternative() != null) {
                 // Update the current storage property criteria with the alternative
                 String currentProperty_Criteria = storagePropertyData.getAlternative();
                 // Increase the assessment points by 1 for finding an alternative
                 assessmentPoints++;
                 // Recursively check opportunities with the alternative storage property criteria
                 return checkPropertyOpportunities(currentProperty_Criteria);
             }
         }
         // Set stopSearch to true if no opportunities were found
         stopSearch = propertyCritteriaList.isEmpty();
         return stopSearch;
     }

     // Evaluates the assessment points
     public String getAssessmentMessage(){

        switch (assessmentPoints) {
            case 0:
                message = MessageManager.getMessage("placeAssessment1"); break;
            case 1, 2:
                message = MessageManager.getMessage("placeAssessment2"); break;
            default:
                message = MessageManager.getMessage("placeAssessment3"); break;
        }
        return  message;
    }

    public int roundUpIfNotInteger(double value) {
        if (value % 1 == 0) {
            return (int) value;
        } else {
            return (int) Math.ceil(value);
        }
    }



}
