package com.ueprojet.appSignalement.stats.dto;

import lombok.Data;

@Data
public class SignalementParArrondissementDTO {
    private String arrondissement;
    private String semaine;
    private Long total;

    public SignalementParArrondissementDTO(String arrondissement, String semaine, Long total) {
        this.arrondissement = arrondissement;
        this.semaine = semaine;
        this.total = total;
    }
    
}
