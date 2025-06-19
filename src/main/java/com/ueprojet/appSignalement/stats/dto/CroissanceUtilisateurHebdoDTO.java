package com.ueprojet.appSignalement.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CroissanceUtilisateurHebdoDTO {
    private String semaine;
    private Long total;
}
