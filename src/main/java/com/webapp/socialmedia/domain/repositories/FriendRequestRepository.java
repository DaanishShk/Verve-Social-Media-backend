package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findFriendRequestByReceiver_Username(String username);
    FriendRequest findFriendRequestBySenderAndReceiver(Account sender, Account receiver);
}