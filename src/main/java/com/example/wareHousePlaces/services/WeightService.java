package com.example.wareHousePlaces.services;

import com.example.wareHousePlaces.data.Article_Data;
import com.example.wareHousePlaces.data.StorageAids_Data;
import com.example.wareHousePlaces.repository.Article_Repository;
import com.example.wareHousePlaces.repository.StorageAids_Repository;
import org.springframework.beans.factory.annotation.Autowired;

public class WeightService {
    private double singleWeight, totalWeight, storageAidWeight;
    @Autowired
    private Article_Repository article_Repository;
    private StorageAids_Repository storageAidsRepository;

    public WeightService(Article_Repository articleRepository, StorageAids_Repository storageAidsRepository){

        this.article_Repository = articleRepository;
        this.storageAidsRepository = storageAidsRepository;
    }


    public void setTotalWeight(int articleNumber, int quantity, String storageAids){

        Article_Data articleValues = article_Repository.findByArticleNumber(articleNumber);
        singleWeight = articleValues.getSingleWeight();

        StorageAids_Data  storageAidsData = storageAidsRepository.findByName(storageAids);
        storageAidWeight = Double.parseDouble(storageAidsData.getWeightTara());

        totalWeight = singleWeight * quantity + storageAidWeight;
    }

    public double getTotalWeight(){
        return totalWeight;
    }



}
