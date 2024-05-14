package com.example.wareHousePlaces.repository;



import com.example.wareHousePlaces.data.PalletRack_Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PalletRack_Repository extends CrudRepository<PalletRack_Data,Integer > {
    @Query("SELECT pr FROM palletrack pr WHERE " +
            "(pr.name LIKE %:name% OR :name IS NULL) AND " +
            "(pr.maxWeight >= :maxWeight OR :maxWeight IS NULL) AND " +
            "(pr.maxHeight >= :maxHeight OR :maxHeight IS NULL) AND " +
            "(pr.abc_Criteria LIKE %:abcCriteria% OR :abcCriteria IS NULL) AND " +
            "(pr.storageAids LIKE %:storageAids% OR :storageAids IS NULL) AND " +
            "(pr.storageProperty LIKE %:storageProperty% OR :storageProperty IS NULL) AND " +
            "(pr.free = :free OR :free IS NULL)")
    List<PalletRack_Data> findByCriteria(@Param("name") String name,
                                         @Param("maxWeight") Integer maxWeight,
                                         @Param("maxHeight") String maxHeight,
                                         @Param("abcCriteria") String abcCriteria,
                                         @Param("storageAids") String storageAids,
                                         @Param("storageProperty") String storageProperty,
                                         @Param("free") String free);

    @Query("SELECT pr FROM palletrack pr WHERE pr.free = 'Y'")
    List<PalletRack_Data> findAllFreePlaces();

}
