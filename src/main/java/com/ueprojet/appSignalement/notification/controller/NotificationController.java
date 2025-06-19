package com.ueprojet.appSignalement.notification.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ueprojet.appSignalement.notification.model.Notification;
import com.ueprojet.appSignalement.notification.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/citoyen/{id}")
    public Notification notifierCitoyen(@PathVariable Long id,
                                        @RequestParam Long arrondissementId,
                                        @RequestParam String titre,
                                        @RequestParam String contenu) {
        return notificationService.envoyerNotificationAUnUser(id,arrondissementId, titre, contenu);
    }

    @PostMapping("/arrondissement/{id}")
    public void notifierArrondissement(@PathVariable Long id,
                                       @RequestParam String titre,
                                       @RequestParam String contenu) {
        notificationService.envoyerNotificationAArrondissement(id, titre, contenu);
    }

    @GetMapping("/citoyen/getNotifications/{id}")
    public List<Notification> getNotificationsCitoyen(@PathVariable Long id) {
        return notificationService.getNotificationsCitoyen(id);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteNotificationsByUserId(@PathVariable Long userId) {
        notificationService.deleteNotificationsByUserId(userId);
    }
}
