package com.example.wareHousePlaces.repository;





import com.example.wareHousePlaces.data.ABC_Criteria_Data;
import com.example.wareHousePlaces.data.StorageProperty_Data;
import org.springframework.data.repository.CrudRepository;

public interface StorageProperty_Repository extends CrudRepository<StorageProperty_Data,Integer > {
    StorageProperty_Data findByName(String name);

}
