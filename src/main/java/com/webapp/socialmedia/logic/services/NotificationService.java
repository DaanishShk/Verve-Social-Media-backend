package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.notifications.Notification;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SimpMessagingTemplate websocket;

    public List<Notification> getNotifications(Account account) {
        return notificationRepository.findByOwnerAccountOrderByTimestampDesc(account);
    }

    public Long numberOfNotViewedNotifications(Account account) {
        return notificationRepository.countNotificationsByOwnerAccountAndViewedIsFalse(account);
    }

    public void createNotification(String message, Account ownerAccount, Account contentAccount, Post post, NotificationType type) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setOwnerAccount(ownerAccount);
        notification.setMessageAccount(contentAccount);
        notification.setPost(post);
        notification.setType(type);
        notification.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        notification.setViewed(false);
        notificationRepository.save(notification);
    }

    public void notifyUser(Account ownerAccount) {
        this.websocket.convertAndSendToUser(ownerAccount.getUsername(), "/notifications", "New notification");       // /user/{username}/destination
    }

    public boolean doesPostNotificationExist(String message, Account account, Post post) {
        return notificationRepository.existsNotificationByMessageAndOwnerAccountAndPostAndType(message, account, post, NotificationType.POST);
    }

    @Transactional
    public void removePostNotifications(Post post) {
        notificationRepository.deleteNotificationsByPost(post);
    }
}
