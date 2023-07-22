package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.config.exceptions.custom.PrivatePostException;
import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.post.Visibility;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private VoteService voteService;



    public Post saveNewPost(Post post) {
        post.setComments(new ArrayList<>());
//        if(post.getType() == PostType.IMAGE) {
//            post.setDimensions(ImageService.getImageDimensionFromUrl(new URL(post.getContent())));
//            System.out.println(post.getDimensions());
//        }
        post.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.getByUsername(username);
        post.setAccount(account);
        this.incrementTotalPosts(account);

        postRepository.save(post);
        return post;
    }

    @Transactional
    @Async
    void incrementTotalPosts(Account account) {
        account.getStats().setTotalPosts(account.getStats().getTotalPosts()+1);
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }

    public Post getPostById(Long id) {
        return postRepository.getById(id);
    }


    public Post getPost(Long id) {
        List<Post> posts = this.getPostsFromQuery(null,
                null,
                id,
                "timestamp"
        );

        if(posts.isEmpty()) {
            throw new IllegalStateException("post does not exist");
        }

        Post post = posts.get(0);
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(post.getAccount().getUsername().equals(account.getUsername())) {
            return post;
        } else if(post.getVisibility()==Visibility.FRIENDS && !account.getFriends().contains(post.getAccount())) {
            throw new PrivatePostException("post is only visible to friends");
        }

        return post;

    }


    public List<Post> getPostsByUsername(String username) {
        return postRepository.findPostsByAccount_Username(username);
    }

    public List<Post> allPublicPosts(String sortCol) {
        List<Post> posts = this.getPostsFromQuery(List.of(Visibility.PUBLIC),
                null,
                null,
                sortCol
        );
        return posts;
    }

    public List<Post> allFollowingPosts(String sortCol) {
        Set<Account> following = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFollowing();
        List<Post> posts = this.getPostsFromQuery(List.of(Visibility.PUBLIC),
                following,
                null,
                sortCol
        );
        return posts;
    }

    public List<Post> allFriendsPosts(String sortCol) {
        Set<Account> friends = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFriends();
        List<Post> posts = this.getPostsFromQuery(
                null,                       // List.of(Visibility.PUBLIC, Visibility.FRIENDS) was causing an exception
                friends,
                null,
                sortCol
        );
        return posts;
    }

    public List<Post> allProfilePosts(boolean isFriend, Account profileAccount, String sortCol) {

        List<Post> posts = this.getPostsFromQuery(
                isFriend || profileAccount.getUsername().equals(
                        SecurityContextHolder.getContext().getAuthentication().getName())?
                        null: List.of(Visibility.PUBLIC),
                Set.of(profileAccount),
                null,
                sortCol
        );
        return posts;
    }

    public String postVote(Vote vote, Long id) {
        voteService.postVote(vote, id);
        return "post voted successfully";
    }

    public List<Post> getPostsFromQuery(List<Visibility> visibility, Set<Account> accounts, Long singlePostId, String sortCol) {
        Account user = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return postRepository.getPostsWithVotesAndUserVoteType(visibility,
                        user,
                        accounts,
                        singlePostId,
                        Sort.by(Sort.Direction.DESC, sortCol)
                        )
                .stream()
                .map((postDto -> new Post(postDto.getPost(),
                        postDto.getLikes() / zeroCheckForCommentsLength(postDto),
                        postDto.getDislikes() / zeroCheckForCommentsLength(postDto),
                        postDto.getUserVoteType() / (int)zeroCheckForCommentsLength(postDto) ,
                        null,
                        postDto.getCommentsLength())))
                .collect(Collectors.toList());
    }

    private long zeroCheckForCommentsLength(PostRepository.PostDto postDto) {
        return postDto.getCommentsLength() != 0 ? postDto.getCommentsLength(): 1;
    }
}
