package com.example.wareHousePlaces.repository;




import com.example.wareHousePlaces.data.ABC_Criteria_Data;
import com.example.wareHousePlaces.data.Article_Data;
import org.springframework.data.repository.CrudRepository;

public interface ABC_Criteria_Repository extends CrudRepository<ABC_Criteria_Data,Integer > {
    ABC_Criteria_Data findByName(String name);

}
