package com.ueprojet.appSignalement.notification.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.ueprojet.appSignalement.notification.enumeration.NotificationType;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String contenu;
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Long userId;

    private Long arrondissementId;

    // Getters et Setters
}

