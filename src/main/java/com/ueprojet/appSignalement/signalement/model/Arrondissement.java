package com.ueprojet.appSignalement.signalement.model;

import com.ueprojet.appSignalement.signalement.enumeration.ArrondissementStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Arrondissement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String region;

    @Enumerated(EnumType.STRING)
    private ArrondissementStatus statut;

    private Double area;

    private String geo; // Utilisez un type approprié pour les coordonnées

}
