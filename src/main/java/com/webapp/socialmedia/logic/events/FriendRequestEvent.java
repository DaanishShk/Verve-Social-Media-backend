package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.logic.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class FriendRequestEvent {

    @Autowired
    private NotificationService notificationService;
    private String confirmation = "%s accepted your friend request";
    private String request = "%s has sent a friend request";

    @Async
    public void requestConfirmation(FriendRequest friendRequest) {
        if (friendRequest.getRequestState() == null) return;
        notificationService.createNotification(
                String.format(confirmation, friendRequest.getReceiver().getDisplayName()),
                friendRequest.getSender(),
                friendRequest.getReceiver(),
                null,
                NotificationType.FRIEND_REQUEST);
        notificationService.notifyUser(friendRequest.getSender());
    }

    @Async
    public void requestSent(FriendRequest friendRequest) {
        notificationService.createNotification(
                String.format(request, friendRequest.getSender().getDisplayName()),
                friendRequest.getReceiver(),
                friendRequest.getSender(),
                null,
                NotificationType.FRIEND_REQUEST);
        notificationService.notifyUser(friendRequest.getReceiver());
    }
}
