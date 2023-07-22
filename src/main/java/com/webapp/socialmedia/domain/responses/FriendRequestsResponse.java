package com.webapp.socialmedia.domain.responses;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class FriendRequestsResponse {
    private FriendRequest friendRequest;
    private Set<Account> friends;
}
