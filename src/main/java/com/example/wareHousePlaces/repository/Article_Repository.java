package com.example.wareHousePlaces.repository;



import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.PalletRack_Data;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Article_Repository extends CrudRepository<Article_Data,Integer > {
    Article_Data findByArticleNumber(int articleNumber);


}
