package com.ueprojet.appSignalement.signalement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ueprojet.appSignalement.signalement.model.Signalement;

@Repository
public interface SignalementRepository extends JpaRepository<Signalement, Long>{
    List<Signalement> findByUserId(Long userId);
}
