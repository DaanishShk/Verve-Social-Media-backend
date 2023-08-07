package com.webapp.socialmedia.logic.events;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.notifications.NotificationType;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.logic.services.AccountService;
import com.webapp.socialmedia.logic.services.NotificationService;
import com.webapp.socialmedia.logic.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommentEvent {

    private final Pattern userTag = Pattern.compile("@(\\w*)\\s?");
    private final String messageFormat = "%s tagged you recently";
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PostService postService;

    public void tagUsers(Comment comment, Account account) {
        Matcher matcher = userTag.matcher(comment.getContent());
        Post post = new Post();
        post.setId(comment.getPostId());
        while (matcher.find()) {
            Account taggedAccount = accountService.getByUsername(matcher.group(1));
            if (taggedAccount == null) continue;
            notificationService.createNotification(
                    String.format(messageFormat, account.getDisplayName()),
                    taggedAccount,
                    account,
                    post,
                    NotificationType.TAG
            );
            notificationService.notifyUser(taggedAccount);
        }
    }
}
