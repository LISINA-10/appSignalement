package com.ueprojet.appSignalement.stats.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SignalementComparatifDTO {
    private LocalDate periode; // ex : "2025-W18" ou "2025-05-28"
    private Long effectues;
    private Long traites;

    public SignalementComparatifDTO(LocalDate periode, Long effectues, Long traites) {
        this.periode = periode;
        this.effectues = effectues;
        this.traites = traites;
    }

    // Getters et setters
}

