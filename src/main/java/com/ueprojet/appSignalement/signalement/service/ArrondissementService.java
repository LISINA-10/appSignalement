package com.ueprojet.appSignalement.signalement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.repository.ArrondissementRepository;

@Service
public class ArrondissementService {

    @Autowired
    ArrondissementRepository arrondissementRepository;

    public void save(Arrondissement arrondissement){
        arrondissementRepository.save(arrondissement);
    }
    
}
