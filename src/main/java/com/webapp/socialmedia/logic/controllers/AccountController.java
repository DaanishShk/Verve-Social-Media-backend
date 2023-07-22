package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.responses.AccountProfileResponse;
import com.webapp.socialmedia.domain.responses.FollowResponse;
import com.webapp.socialmedia.domain.responses.UsernameCheck;
import com.webapp.socialmedia.logic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountProfileService accountProfileService;

//    @Autowired
//    private ActivationService activationService;


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String registration(@RequestBody Account account) { // RequestParam only works for query parameters
        accountService.addAccount(account);
        accountProfileService.saveProfile(new AccountProfile(account));
//        activationService.sendActivationEmail(account);
        return "registration successful";
    }

    @GetMapping
    public UsernameCheck checkUsernameValidity(@RequestParam String username) {
        return new UsernameCheck(!accountService.checkIfUsernameExists(username));
    }

    @GetMapping("/{username}")
    public Account accountDetails(@PathVariable String username) {
        return accountService.getByUsername(username);
    }

    @GetMapping("/{username}/profile")
    public AccountProfileResponse getProfile(@PathVariable String username) { // username account and authentication account are different
        return accountProfileService.getProfilePageResponse(username);
    }

//    @GetMapping
//    public List<Account> all() {
//        return accountService.all();
//    }

    @PostMapping("/following")
    private FollowResponse updateFollowingList(@RequestBody Account followAccount) {
        return accountService.updateFollowStatus(followAccount);
    }

    @GetMapping("/friends")
    public Set<Account> getFriends() {
        return accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFriends();
    }

    @GetMapping("/following")
    public Set<Account> getFollowing() {
        return accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFollowing();
    }

    @DeleteMapping("/friends")
    public String removeFriend(@RequestBody Account friend) {
        return accountService.removeFriend(friend);
    }

    @DeleteMapping("/following")
    public String removeFollowing(@RequestBody Account following) {
        return accountService.removeFollowing(following);
    }
}
