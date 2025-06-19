package com.ueprojet.appSignalement.signalement.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ueprojet.appSignalement.notification.service.EmailService;
import com.ueprojet.appSignalement.signalement.enumeration.ArrondissementStatus;
import com.ueprojet.appSignalement.signalement.enumeration.Status;
import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.model.Signalement;
import com.ueprojet.appSignalement.signalement.repository.ArrondissementRepository;
import com.ueprojet.appSignalement.signalement.repository.SignalementRepository;
import com.ueprojet.appSignalement.usersmanagement.model.Citizen;
import com.ueprojet.appSignalement.usersmanagement.repositorie.UsersRepository;

@Service
public class SignalementService {
    
    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ArrondissementRepository arrondissementRepository;

    public Signalement creerSignalement(Signalement signalement) {

        Long citoyenId = signalement.getUserId();

        Long nbSignalements = signalementRepository.countByCitoyenToday(citoyenId);
        if (nbSignalements >= 10) {
            throw new RuntimeException("Limite quotidienne de signalements atteinte (10/jour)");
        }

        signalement.setDate(LocalDateTime.now()); // si ce n’est pas déjà fait
        return signalementRepository.save(signalement);
    }


    public List<Signalement> getSignalementsByUserId(Long userId) {
        return signalementRepository.findByUserId(userId);
    }

    public List<Signalement> getSignalementsByArrondissement(long arrondissementId) {
        
        return signalementRepository.findByArrondissementId(arrondissementId);
    }

    public Signalement updateSignalementStatus(Long id, Status status) {

        // Vérifier si l'arrondissement est bloqué avant de mettre à jour le signalement
        Signalement signalement = signalementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Signalement not found"));
        Arrondissement arrondissement = arrondissementRepository.findById(signalement.getArrondissementId())
                .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        if (arrondissement.getStatut() == ArrondissementStatus.BLOQUE) {
            throw new RuntimeException("L'arrondissement est bloqué. Action impossible.");
        }

        signalement.setStatus(status);
        signalementRepository.save(signalement);

        // Envoyer un mail au citoyen concerné
        Long idCitoyen = signalement.getUserId(); // Assure-toi que Signalement a bien un champ Citoyen
        Citizen citoyen = (Citizen)usersRepository.findById(idCitoyen).orElseThrow(() -> new RuntimeException("Citizen not found"));
        String email = citoyen.getEmail(); // Assure-toi que Citoyen a bien un champ email
        String subject = "Mise à jour de votre signalement";
        String body = "Bonjour, votre signalement n°" + signalement.getId() + " a été mis à jour au statut : " + status.toString() + ".\n"+ 
                      "Merci de votre patience.\n" +
                      "Cordialement,\n" +
                      "L'équipe de gestion des signalements.";

        emailService.sendEmail(email, subject, body);
        return signalement; 
    }

    public List<Signalement> getAllSignalements() {
        return signalementRepository.findAll();
    }

    public List<Signalement> getSignalementsByArrondissementIdAndStatus(Long arrondissementId) {

        // Vérifier si l'arrondissement est bloqué avant de récupérer les signalements
        Arrondissement arrondissement = arrondissementRepository.findById(arrondissementId)
                .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        if (arrondissement.getStatut() == ArrondissementStatus.BLOQUE) {
            throw new RuntimeException("L'arrondissement est bloqué. Action impossible.");
        }


        List<Status> statuts = Arrays.asList(Status.EN_ATTENTE, Status.RECU);
        return signalementRepository.findByArrondissementIdAndStatusIn(arrondissementId, statuts);
    }

    public int[] getSignalementsByArrondissementAndWeek(Long arrondissementId) {
        int[] counts = new int[8]; // 8 semaines
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusWeeks(8);

        for (int week = 0; week < 8; week++) {
            LocalDate weekStart = startDate.plusWeeks(week);
            LocalDate weekEnd = weekStart.plusDays(6);
            List<Signalement> signalements = signalementRepository.findByDateBetweenAndArrondissementId(weekStart, weekEnd, arrondissementId);
            counts[week] = signalements.size();
        }
        return counts;
    }
}
