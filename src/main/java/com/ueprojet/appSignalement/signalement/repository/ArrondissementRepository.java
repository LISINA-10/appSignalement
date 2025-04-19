package com.ueprojet.appSignalement.signalement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ueprojet.appSignalement.signalement.model.Arrondissement;

@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {
    
}
