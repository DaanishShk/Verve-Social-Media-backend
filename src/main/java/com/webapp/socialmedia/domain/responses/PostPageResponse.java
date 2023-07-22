package com.webapp.socialmedia.domain.responses;

import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostPageResponse {
    private Post post;
    private AccountProfile profile;
}
