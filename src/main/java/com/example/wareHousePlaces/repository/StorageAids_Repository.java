package com.example.wareHousePlaces.repository;




import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.StorageAids_Data;
import org.springframework.data.repository.CrudRepository;

public interface StorageAids_Repository extends CrudRepository<StorageAids_Data,Integer > {
    StorageAids_Data findByName(String name);

}
