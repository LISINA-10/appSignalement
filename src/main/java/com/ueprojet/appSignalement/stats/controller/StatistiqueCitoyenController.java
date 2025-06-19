package com.ueprojet.appSignalement.stats.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.stats.dto.SignalementComparatifDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementsParProblemeDTO;
import com.ueprojet.appSignalement.stats.service.StatistiqueService;

@RestController
@RequestMapping("/api/citoyen/stats")
public class StatistiqueCitoyenController {

    @Autowired
    private StatistiqueService statistiqueService;

    @GetMapping("/signalements-par-probleme")
    public ResponseEntity<List<SignalementsParProblemeDTO>> getSignalementsParProblemeParSemaine() {
        return ResponseEntity.ok(statistiqueService.getSignalementsParProblemeParSemaineNational());
    }

    @GetMapping("/comparatif-signalements/{arrId}")
    public ResponseEntity<List<SignalementComparatifDTO>> getCourbeComparative(@PathVariable Long arrId) {
        return ResponseEntity.ok(statistiqueService.getCourbeComparativeSignalements(arrId));
    }

}

