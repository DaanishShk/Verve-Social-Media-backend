package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import com.webapp.socialmedia.domain.model.account.relations.FriendRequestState;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.logic.services.NotificationService;
import com.webapp.socialmedia.logic.utils.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.persistence.PostRemove;

@Component
public class FriendRequestListener {


    private String messageFormat = "%s accepted your friend request";

    @PostRemove
    private void sendNotification(FriendRequest friendRequest) {
        NotificationService notificationService = SpringApplicationContext.getBean(NotificationService.class);
        if (friendRequest.getRequestState() == null) return;
        notificationService.createNotification(
                String.format(messageFormat, friendRequest.getReceiver()),
                friendRequest.getSender(),
                friendRequest.getReceiver(),
                null,
                NotificationType.FRIEND_REQUEST);
        notificationService.notifyUser(friendRequest.getSender());
    }
}
