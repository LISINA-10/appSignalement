package com.ueprojet.appSignalement.signalement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.notification.service.NotificationService;
import com.ueprojet.appSignalement.signalement.enumeration.ArrondissementStatus;
import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.repository.ArrondissementRepository;

@Service
public class ArrondissementService {

    @Autowired
    ArrondissementRepository arrondissementRepository;

    @Autowired
    NotificationService notificationService;

    public void save(Arrondissement arrondissement){
        System.out.println("ARR: " + arrondissement);
        arrondissement.setStatut(ArrondissementStatus.ACTIF);
        arrondissementRepository.save(arrondissement);
    }

    public List<Arrondissement> getAllArrondissements() {
        return arrondissementRepository.findAll();
    }

    public void deleteArrondissement(Long id) {
        arrondissementRepository.deleteById(id);
    }

    public void bloquerArrondissement(Long id) {
        Arrondissement a = arrondissementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        a.setStatut(ArrondissementStatus.BLOQUE);
        arrondissementRepository.save(a);
    
        notificationService.envoyerNotificationAgentsParArrondissement(
            a.getId(),
            "Arrondissement bloqué",
            "Votre arrondissement a été temporairement bloqué. Vous ne pouvez plus effectuer d'action."
        );
    }
    
    public void debloquerArrondissement(Long id) {
        Arrondissement a = arrondissementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        a.setStatut(ArrondissementStatus.ACTIF);
        arrondissementRepository.save(a);
    
        notificationService.envoyerNotificationAgentsParArrondissement(
            a.getId(),
            "Arrondissement débloqué",
            "Votre arrondissement a été débloqué. Vous pouvez reprendre vos activités."
        );
    }
    
    
}
