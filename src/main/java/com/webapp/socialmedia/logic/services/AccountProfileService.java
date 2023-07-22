package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.model.stats.VoteType;
import com.webapp.socialmedia.domain.repositories.AccountProfileRepository;
import com.webapp.socialmedia.domain.responses.AccountProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class AccountProfileService {

    @Autowired
    private AccountProfileRepository accountProfileRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PostService postService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private CommentService commentService;

    public void saveProfile(AccountProfile profile) {
        accountProfileRepository.save(profile);
    }

    public AccountProfileResponse getProfilePageResponse(String username) {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        AccountProfile profile = accountProfileRepository.findAccountProfileByAccount_Username(username);
        profile.getAccount().getStats().setTotalLikes(accountProfileRepository.countTotalLikesReceived(profile.getAccount()));

        boolean isFriend = false;
        String friendStatus = "Send Request";
        Account receiver = profile.getAccount(); //accountService.getByUsername(username);
        if(account.getFriends().contains(receiver)) {
            friendStatus = "Request Accepted";
            isFriend = true;
        } else if (friendRequestService.getRequestBySenderAndReceiver(account, receiver) != null) {
            friendStatus = "Pending";
        }
        Set<Account> friends = account.getFriends();
        friends.retainAll(receiver.getFriends());


        String followStatus = "Follow";
        Set<Account> following = account.getFollowing();
        if(following.contains(accountService.getByUsername(username))) {
            followStatus = "Following";
        }
        profile.setNumberFollowing((long)following.size());
        profile.setNumberFollowers(accountService.getNumberFollowers(account));


        List<Post> posts = postService.allProfilePosts(isFriend, profile.getAccount(), "timestamp");
        List<Comment> activityComments = commentService.getActivityComments(isFriend, username);

        return new AccountProfileResponse(profile,
                posts,
                followStatus,
                friendStatus,
                profile.getAccount().getStats(),
                friends,
                activityComments);
    }

    public AccountProfile getProfileFromUsername(String username) {
        AccountProfile profile = accountProfileRepository.findAccountProfileByAccount_Username(username);
        profile.setNumberFollowing((long)profile.getAccount().getFollowing().size());
        profile.setNumberFollowers(accountService.getNumberFollowers(accountService.getByUsername(username)));
        return profile;
    }

    public Image getBackgroundFromUsername(String username) {
        return accountProfileRepository.findAccountProfileByAccount_Username(username).getBackground();
    }

}
