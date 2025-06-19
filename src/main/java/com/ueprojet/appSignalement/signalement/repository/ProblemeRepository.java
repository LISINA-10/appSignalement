package com.ueprojet.appSignalement.signalement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ueprojet.appSignalement.signalement.model.Probleme;

public interface ProblemeRepository  extends JpaRepository<Probleme, Long>{
    
}
