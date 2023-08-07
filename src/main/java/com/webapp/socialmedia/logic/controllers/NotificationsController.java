package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.notifications.Notification;
import com.webapp.socialmedia.logic.services.AccountService;
import com.webapp.socialmedia.logic.services.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    private final Logger logger = LoggerFactory.getLogger(NotificationsController.class);
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Notification> getNotifications() {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return notificationService.getNotifications(account);
    }

    @GetMapping("/new")
    public Long getNotViewedNotifications() {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return notificationService.numberOfNotViewedNotifications(account);
    }
}
