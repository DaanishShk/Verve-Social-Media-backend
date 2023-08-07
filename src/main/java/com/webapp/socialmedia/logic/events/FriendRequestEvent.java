package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.logic.services.NotificationService;
import com.webapp.socialmedia.logic.utils.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostRemove;

@Component
public class FriendRequestEvent {

    @Autowired
    private NotificationService notificationService;
    private String messageFormat = "%s accepted your friend request";

    @PostRemove
    public void sendNotification(FriendRequest friendRequest) {
        if (friendRequest.getRequestState() == null) return;
        notificationService.createNotification(
                String.format(messageFormat, friendRequest.getReceiver().getDisplayName()),
                friendRequest.getSender(),
                friendRequest.getReceiver(),
                null,
                NotificationType.FRIEND_REQUEST);
        notificationService.notifyUser(friendRequest.getSender());
    }
}
