package com.ueprojet.appSignalement.notification.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ueprojet.appSignalement.notification.model.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserId(Long citoyenId);
    void deleteByUserId(Long userId);
}

