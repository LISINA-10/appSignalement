package com.ueprojet.appSignalement.notification.service;


import com.ueprojet.appSignalement.usersmanagement.model.Agent;
import com.ueprojet.appSignalement.usersmanagement.model.Citizen;
import com.ueprojet.appSignalement.usersmanagement.repositorie.UsersRepository;

import com.ueprojet.appSignalement.notification.enumeration.NotificationType;
import com.ueprojet.appSignalement.notification.model.Notification;
import com.ueprojet.appSignalement.notification.repository.NotificationRepository;
import com.ueprojet.appSignalement.signalement.enumeration.ArrondissementStatus;
import com.ueprojet.appSignalement.signalement.model.Arrondissement;
import com.ueprojet.appSignalement.signalement.repository.ArrondissementRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ArrondissementRepository arrondissementRepository;

    


    public Notification envoyerNotificationAUnUser(Long userId, Long arrondissementId,String titre, String contenu) {

        //verivier que l'arrondissement de l'utilisateur n'est pas bloqué
        Arrondissement arrondissement = arrondissementRepository.findById(arrondissementId)
                .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        if (arrondissement.getStatut() == ArrondissementStatus.BLOQUE) {
            throw new RuntimeException("L'arrondissement est bloqué. Action impossible.");
        }

        Notification notif = new Notification();
        notif.setUserId(userId);
        notif.setTitre(titre);
        notif.setContenu(contenu);
        notif.setArrondissementId(arrondissementId);
        notif.setType(NotificationType.TO_USER);
        return notificationRepository.save(notif);
    }

    public void envoyerNotificationAArrondissement(Long arrondissementId, String titre, String contenu) {

        // Vérifier que l'arrondissement n'est pas bloqué avant d'envoyer la notification
        Arrondissement arrondissement = arrondissementRepository.findById(arrondissementId)
                .orElseThrow(() -> new RuntimeException("Arrondissement non trouvé"));
        if (arrondissement.getStatut() == ArrondissementStatus.BLOQUE) {
            throw new RuntimeException("L'arrondissement est bloqué. Action impossible.");
        }
        

        List<Citizen> citoyens = usersRepository.findCitizensByArrondissementId(arrondissementId);
        for (Citizen citoyen : citoyens) {
            Notification notif = new Notification();
            notif.setUserId(citoyen.getId());
            notif.setArrondissementId(arrondissementId);
            notif.setTitre(titre);
            notif.setContenu(contenu);
            notif.setType(NotificationType.TO_ARRONDISSEMENT);
            notificationRepository.save(notif);
        }
    }

    public void envoyerNotificationAgentsParArrondissement(Long arrondissementId, String titre, String contenu) {
        List<Agent> agents = usersRepository.findAgentsByArrondissementId(arrondissementId);
    
        for (Agent agent : agents) {
            Notification notif = new Notification();
            notif.setTitre(titre);
            notif.setContenu(contenu);
            notif.setDate(LocalDateTime.now());
            notif.setUserId(agent.getId());
            notif.setType(NotificationType.TO_USER);
            notificationRepository.save(notif);
        }
    }



    public List<Notification> getNotificationsCitoyen(Long citoyenId) {
        return notificationRepository.findByUserId(citoyenId);
    }

    public void deleteNotificationsByUserId(Long userId) {
        notificationRepository.deleteByUserId(userId);
    }
}
