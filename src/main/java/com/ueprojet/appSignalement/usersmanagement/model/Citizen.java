package com.ueprojet.appSignalement.usersmanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@DiscriminatorValue("CITIZEN")
public class Citizen extends Users{

    private String email;

    private Long arrondissementId;
    
}
