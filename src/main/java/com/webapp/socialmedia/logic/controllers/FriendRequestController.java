package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.account.relations.FriendRequest;
import com.webapp.socialmedia.domain.responses.FriendRequestsResponse;
import com.webapp.socialmedia.logic.services.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/friends/requests")
public class FriendRequestController {

    @Autowired
    private FriendRequestService friendRequestService;

    @PostMapping
    public String sendRequest(@RequestBody FriendRequest friendRequest) {
        friendRequestService.addRequest(friendRequest);
        return "friend request added successfully";
    }

    @GetMapping
    public List<FriendRequestsResponse> getRequests() {
        return friendRequestService.getRequestsByReceiver();
    }

    @DeleteMapping
    public String completeRequest(@RequestBody FriendRequest friendRequest) {
        return friendRequestService.completeRequest(friendRequest);
    }
}
