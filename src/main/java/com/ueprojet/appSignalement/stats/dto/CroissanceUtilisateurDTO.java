package com.ueprojet.appSignalement.stats.dto;

import java.time.LocalDate;

//import java.time.LocalDate;

import lombok.Data;

@Data
public class CroissanceUtilisateurDTO {
    private LocalDate date;
    private Long nombreUtilisateurs;

    public CroissanceUtilisateurDTO(LocalDate date, Long nombreUtilisateurs) {
        this.date = date;
        this.nombreUtilisateurs = nombreUtilisateurs;
    }


    
}