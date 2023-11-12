package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.model.stats.ProfileStats;
import com.webapp.socialmedia.domain.repositories.AccountProfileRepository;
import com.webapp.socialmedia.domain.repositories.AccountRepository;
import com.webapp.socialmedia.domain.responses.FollowResponse;
import com.webapp.socialmedia.logic.events.AccountEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountEvent accountEvent;
    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "accounts", key = "#username")
    public Account getByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }


    public void addAccount(Account account) {
        boolean emailExists = accountRepository.findAccountByEmail(account.getEmail()).isPresent();
        boolean userExists = accountRepository.findAccountByUsername(account.getUsername()) != null;
//        TODO: add username check as well

        if (emailExists) {
            throw new IllegalStateException("email already taken");
        }

        if (userExists) {
            throw new IllegalStateException("username already taken");
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setStats(new ProfileStats(0L, 0L, 0L));
        account.setDisplayName(account.getUsername());

        this.saveAccount(account);
    }

    //    @CacheEvict(value = "accounts", key = "#account.username")
    public void saveAccount(Account account) {
        cacheManager.getCache("accounts").evict(account.getUsername());
        accountRepository.save(account);
    }

    public List<Account> all() {
        return accountRepository.findAll();
    }

    public FollowResponse updateFollowStatus(Account followAccount) {

        Account account = accountRepository.findAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Set<Account> following = account.getFollowing();

        if (following.contains(followAccount)) {     // how is this working?
            following.remove(followAccount);
            this.saveAccount(account);
            return new FollowResponse("Follow");
        }
        followAccount = accountRepository.getById(followAccount.getId());
        following.add(followAccount);
        this.saveAccount(account);

        accountEvent.followerCountNotification(followAccount, getNumberFollowers(followAccount));

        return new FollowResponse("Following");
//                System.out.println("Follow Account = "+followAccount.hashCode());
//        for(Account a: following) {           used to check if hash value is the same (possibly id dependent only)
//            System.out.println(a.hashCode());
//        }
    }

    public String removeFriend(Account friend) {
        Account account = accountRepository.findAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        friend = accountRepository.findAccountByUsername(friend.getUsername());
        account.getFriends().remove(friend);
        friend.getFriends().remove(account);
        this.saveAccount(friend);
        this.saveAccount(account);
        return "removed from friends";
    }

    public String removeFollowing(@RequestBody Account following) {
        Account account = accountRepository.findAccountByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        account.getFollowing().remove(following);
        this.saveAccount(account);
        return "removed from following";
    }

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }


//    public void enableAccount(Account account) {
//        account.setEnabled(true);
//        accountRepository.save(account);
//    }

    public Long getNumberFollowers(Account account) {
        return accountRepository.countAccountsByFollowing(account);
    }

    public boolean checkIfUsernameExists(String username) {
        return accountRepository.existsAccountByUsername(username);
    }

    public Image getProfilePictureFromUsername(String username) {
        return accountRepository.findImageByAccountUsername(username);
    }
}
