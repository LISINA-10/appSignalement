package com.ueprojet.appSignalement.signalement.model;

import java.time.LocalDateTime;

import com.ueprojet.appSignalement.signalement.enumeration.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Signalement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String description;

    
    private Status status;

    private String imageUrl;
    private double latitude;
    private double longitude;
    private long arrondissementId;
    private Long userId;
    private Long problemeId;
    private LocalDateTime date;

    
}
