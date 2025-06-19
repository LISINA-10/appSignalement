package com.ueprojet.appSignalement.stats.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurDTO;
import com.ueprojet.appSignalement.stats.dto.CroissanceUtilisateurHebdoDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementParArrondissementDTO;
import com.ueprojet.appSignalement.stats.dto.SignalementsParProblemeDTO;
import com.ueprojet.appSignalement.stats.dto.StatutSignalementDTO;
import com.ueprojet.appSignalement.stats.service.StatistiqueService;

@RestController
@RequestMapping("/api/stats")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;
    //admin
    @GetMapping("/admin/signalements-par-arrondissement")
    public List<SignalementParArrondissementDTO> getSignalementsParArrondissementParSemaine() {
        return statistiqueService.getStatsParArrondissementParSemaine();
    }
    //admin
    @GetMapping("/admin/croissance-utilisateurs")
    public ResponseEntity<List<CroissanceUtilisateurDTO>> getCroissanceUtilisateursParJour() {
        return ResponseEntity.ok(statistiqueService.getCroissanceUtilisateursParJour());
    }
    //agent
    @GetMapping("/agent/signalements-par-probleme/{arrId}")
    public ResponseEntity<List<SignalementsParProblemeDTO>> getSignalementsParProblemeParSemaine(@PathVariable Long arrId) {
        return ResponseEntity.ok(statistiqueService.getSignalementsParProblemeParSemaine(arrId));
    }
    //agent
    @GetMapping("/agent/croissance-utilisateurs/{arrId}")
    public ResponseEntity<List<CroissanceUtilisateurHebdoDTO>> getCroissanceUtilisateursHebdo(@PathVariable Long arrId) {
        return ResponseEntity.ok(statistiqueService.getCroissanceUtilisateursHebdo(arrId));
    }
    //agent
    @GetMapping("/agent/statuts-signalements/{arrId}")
    public ResponseEntity<List<StatutSignalementDTO>> getStatutSignalements(@PathVariable Long arrId) {
        return ResponseEntity.ok(statistiqueService.getStatutSignalements(arrId));
    }




}

