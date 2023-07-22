package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import com.webapp.socialmedia.domain.model.account.relations.FriendRequestState;
import com.webapp.socialmedia.domain.repositories.FriendRequestRepository;
import com.webapp.socialmedia.domain.responses.FriendRequestsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FriendRequestService {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private AccountService accountService;

    public void addRequest(FriendRequest friendRequest) {
        Account sender = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        friendRequest.setSender(sender);
        friendRequest.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        friendRequestRepository.save(friendRequest);
    }

    public List<FriendRequestsResponse> getRequestsByReceiver() {
        Account receiver = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Account> friends = receiver.getFriends();
        List<FriendRequest> list = friendRequestRepository.findFriendRequestByReceiver_Username(receiver.getUsername());
        List<FriendRequestsResponse> response = new ArrayList<>();
        for(FriendRequest fr: list) {
            Set<Account> common = fr.getSender().getFriends();
            common.retainAll(friends);
            response.add(new FriendRequestsResponse(fr, common));
        }
        return response;
    }

    public String completeRequest(FriendRequest friendRequest) {
        if (friendRequest.getRequestState() == FriendRequestState.ACCEPTED) {
            friendRequest = friendRequestRepository.getById(friendRequest.getId());
            Account sender = friendRequest.getSender();
            Account receiver = friendRequest.getReceiver();
            if(!SecurityContextHolder.getContext().getAuthentication().getName().equals(receiver.getUsername())) {
                return "Receiver id does not match";
            }
            sender.getFriends().add(receiver);
            receiver.getFriends().add(sender);
            accountService.saveAccount(sender);
            accountService.saveAccount(receiver);
        }
        friendRequestRepository.delete(friendRequest);
        return "request processed successfully";
    }

    public FriendRequest getRequestBySenderAndReceiver(Account sender, Account receiver) {
        return friendRequestRepository.findFriendRequestBySenderAndReceiver(sender, receiver);
    }
}
