package com.ueprojet.appSignalement.signalement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.service.ArrondissementService;

@RestController
@RequestMapping("/api/arrondissements")
public class ArrondissementController {

    @Autowired
    ArrondissementService arrondissementService;

    @PostMapping("/create/arrondissement")
    public void createArrondissement(@RequestBody Arrondissement arrondissement){
        arrondissementService.save(arrondissement);
    }

    @GetMapping("/getAllArrondissements")
    public List<Arrondissement> getAllArrondissements() {
        return arrondissementService.getAllArrondissements();
    }

    @DeleteMapping("/deleteArrondissement/{id}")
    public void deleteArrondissement(@PathVariable Long id) {
        arrondissementService.deleteArrondissement(id);
    }

    // ✅ Bloquer un arrondissement
    @PostMapping("/{id}/bloquer")
    public ResponseEntity<String> bloquerArrondissement(@PathVariable Long id) {
        arrondissementService.bloquerArrondissement(id);
        return ResponseEntity.ok("Arrondissement bloqué avec succès.");
    }

    // ✅ Débloquer un arrondissement
    @PostMapping("/{id}/debloquer")
    public ResponseEntity<String> debloquerArrondissement(@PathVariable Long id) {
        arrondissementService.debloquerArrondissement(id);
        return ResponseEntity.ok("Arrondissement débloqué avec succès.");
    }

}
