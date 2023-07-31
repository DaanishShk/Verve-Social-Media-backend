package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.post.Visibility;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.responses.PostPageResponse;
import com.webapp.socialmedia.logic.services.AccountProfileService;
import com.webapp.socialmedia.logic.services.CommentService;
import com.webapp.socialmedia.logic.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private AccountProfileService accountProfileService;

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Post submitNewPost(@RequestBody Post post) {  // todo: check if .valueof is sufficient
        return postService.saveNewPost(post);
    }

    @GetMapping
    private List<Post> getPosts(@RequestParam String tab, @RequestParam String sort) {
        if(tab.equals("public"))
            return postService.allPublicPosts(sort);
        if(tab.equals("following"))
            return postService.allFollowingPosts(sort);
        if(tab.equals("friends"))
            return postService.allFriendsPosts(sort);
        return new ArrayList<>();
    }

    @GetMapping("/{id}")
    private PostPageResponse getById(@PathVariable Long id) {
        Post post = postService.getPost(id);
        List<Comment> comments = commentService.getCommentsByPostId(post.getId());
        post.setComments(comments);
        AccountProfile profile = accountProfileService.getProfileFromUsername(post.getAccount().getUsername());
        return new PostPageResponse(post, profile);
    }

    @PostMapping("/{id}/votes")
    public String postVote(@RequestBody Vote vote, @PathVariable Long id) {
        return postService.postVote(vote, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deletePost(@PathVariable Long id) {
        return postService.removePost(id);
    }

    @GetMapping("/test")
    public List<Post> getJPQL() {
        return postService.getPostsFromQuery(List.of(Visibility.PUBLIC),
                null,
                null,
                "timestamp"
        );
    }
}
