package com.ueprojet.appSignalement.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignalementsParProblemeDTO {
    private String nomProbleme;
    private Long semaine;
    private Long total;
}
