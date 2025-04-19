package com.ueprojet.appSignalement.signalement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.signalement.model.Signalement;
import com.ueprojet.appSignalement.signalement.repository.SignalementRepository;

@Service
public class SignalementService {
    
    @Autowired
    private SignalementRepository signalementRepository;

    public Signalement saveSignalement(Signalement signalement) {
        return signalementRepository.save(signalement);
    }

    public List<Signalement> getSignalementsByUserId(Long userId) {
        return signalementRepository.findByUserId(userId);
    }

    public List<Signalement> getAllSignalements() {
        return signalementRepository.findAll();
    }
}
