package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.notifications.Notification;
import com.webapp.socialmedia.domain.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private  SimpMessagingTemplate websocket;

    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

    public void notifyUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        this.websocket.convertAndSendToUser(username,  "/notifications","New notification");       // /user/{username}/destination
    }
}
