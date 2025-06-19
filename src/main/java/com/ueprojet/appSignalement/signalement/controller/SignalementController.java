package com.ueprojet.appSignalement.signalement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.signalement.enumeration.Status;

import com.ueprojet.appSignalement.signalement.model.Signalement;
import com.ueprojet.appSignalement.signalement.service.SignalementService;

@RestController
@RequestMapping("api/signalements")
public class SignalementController {
 
    @Autowired
    SignalementService signalementService;

    @PostMapping("/create")
    public Signalement saveSignalement(@RequestBody Signalement signalement) {
        return signalementService.creerSignalement(signalement);
    }

    @GetMapping("/getSignalements/{arrondissementId}")
    public ResponseEntity<List<Signalement>> getSignalementsByArrondissement(@PathVariable long arrondissementId) {
        List<Signalement> signalements = signalementService.getSignalementsByArrondissement(arrondissementId);
        return ResponseEntity.ok(signalements);
    }

    @GetMapping("/getSignalements/users/{userId}")
    public ResponseEntity<List<Signalement>> getSignalementsByUserId(@PathVariable long userId) {
        List<Signalement> signalements = signalementService.getSignalementsByArrondissement(userId);
        return ResponseEntity.ok(signalements);
    }

    @PutMapping("/statusUpdate/{id}")
    public ResponseEntity<Signalement> updateSignalementStatus(@PathVariable Long id, @RequestParam Status status) {
        Signalement updatedSignalement = signalementService.updateSignalementStatus(id, status);
        return ResponseEntity.ok(updatedSignalement);
    }

    @GetMapping("getSignalementByStatus/{arrondissementId}")
    public List<Signalement> getSignalementsByArrondissementIdAndStatus(@PathVariable Long arrondissementId) {
        return signalementService.getSignalementsByArrondissementIdAndStatus(arrondissementId);
    }

    

    
}
