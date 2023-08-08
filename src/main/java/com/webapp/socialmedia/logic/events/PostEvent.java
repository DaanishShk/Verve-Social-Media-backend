package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.logic.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostEvent {

    @Autowired
    private NotificationService notificationService;
    private String likesCount = "Your post just hit %d likes!";
    private String commentCount = "Your post has %d comments";
    private List<Integer> count = List.of(5, 10, 25, 50, 75, 100, 250, 500, 1000);

    @Async
    public void postStatus(Post post) {
        int likes = count.stream().reduce((c, curr) -> c <= post.getCountVotes().getLikes() ? c : curr).orElse(0);
        int comments = count.stream().reduce((c, curr) -> c <= post.getCommentsLength() ? c : curr).orElse(0);

        if (count.contains(likes)) {
            notificationService.createNotification(
                    String.format(likesCount, likes),
                    post.getAccount(),
                    post.getAccount(),
                    post,
                    NotificationType.POST);
            notificationService.notifyUser(post.getAccount());
        }

        if (count.contains(comments)) {
            notificationService.createNotification(
                    String.format(commentCount, comments),
                    post.getAccount(),
                    post.getAccount(),
                    post,
                    NotificationType.POST);
            notificationService.notifyUser(post.getAccount());
        }

    }
}
