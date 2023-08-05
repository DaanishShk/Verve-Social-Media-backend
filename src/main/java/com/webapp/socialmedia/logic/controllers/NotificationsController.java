package com.webapp.socialmedia.logic.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {

    @SendTo("/user/notifications")
    public String taggedUser(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return message;
    }
}
