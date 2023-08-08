package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.logic.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.persistence.PostRemove;
import java.util.List;

@Component
public class AccountEvent {

    @Autowired
    private NotificationService notificationService;

    private String postCountFormat = "Your total posts has reached %d";
    private String commentCountFormat = "Your total comments has reached %d";
    private String followerCountFormat = "You now have %d followers!";
    private List<Integer> count = List.of(5, 10, 25, 50, 75, 100, 250, 500, 1000);

    @Async
    public void postCountNotification(Account account) {
        if (!count.contains(account.getStats().getTotalPosts().intValue())) return;
        notificationService.createNotification(
                String.format(postCountFormat, account.getStats().getTotalPosts()),
                account,
                account,
                null,
                NotificationType.ACHIEVEMENT);
        notificationService.notifyUser(account);
    }

    @Async
    public void commentCountNotification(Account account) {
        if (!count.contains(account.getStats().getTotalComments().intValue())) return;
        notificationService.createNotification(
                String.format(commentCountFormat, account.getStats().getTotalComments()),
                account,
                account,
                null,
                NotificationType.ACHIEVEMENT);
        notificationService.notifyUser(account);
    }

    @Async
    public void followerCountNotification(Account account, Long followers) {
        if (!count.contains(followers.intValue())) return;
        notificationService.createNotification(
                String.format(followerCountFormat, followers),
                account,
                account,
                null,
                NotificationType.ACHIEVEMENT);
        notificationService.notifyUser(account);
    }
}
