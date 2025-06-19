package com.ueprojet.appSignalement.stats.dto;

import com.ueprojet.appSignalement.signalement.enumeration.Status;


import lombok.Data;

@Data
public class StatutSignalementDTO {
    private Status statut;
    private Long total;

    public StatutSignalementDTO(Status statut, Long total){
        this.statut = statut;
        this.total = total;
    }
}
