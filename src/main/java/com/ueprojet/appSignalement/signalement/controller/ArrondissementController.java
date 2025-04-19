package com.ueprojet.appSignalement.signalement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.service.ArrondissementService;

@RestController
@RequestMapping("api/signalement")
public class ArrondissementController {

    @Autowired
    ArrondissementService arrondissementService;

    @PostMapping("/create/arrondissement")
    public void createArrondissement(Arrondissement arrondissement){
        arrondissementService.save(arrondissement);
    }

    
}
