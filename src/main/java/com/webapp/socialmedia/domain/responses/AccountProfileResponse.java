package com.webapp.socialmedia.domain.responses;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.ProfileStats;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class AccountProfileResponse {
    private AccountProfile profile;
    private List<Post> posts;
    private String followStatus;
    private String friendStatus;
    private ProfileStats stats;
    private Set<Account> friends;
    private List<Comment> activityComments;
}
