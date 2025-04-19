package com.ueprojet.appSignalement.usersmanagement.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
@DiscriminatorValue("AGENT")
public class Agent extends Users {

    private Long arrondissementId;

}
