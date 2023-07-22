//package com.webapp.socialmedia.logic.controllers;
//
//import com.webapp.socialmedia.domain.model.account.Account;
//import com.webapp.socialmedia.domain.model.comment.Comment;
//import com.webapp.socialmedia.domain.model.post.Post;
//import com.webapp.socialmedia.domain.model.stats.CountVotes;
//import com.webapp.socialmedia.domain.model.stats.Vote;
//import com.webapp.socialmedia.domain.model.stats.VoteType;
//import com.webapp.socialmedia.logic.services.AccountService;
//import com.webapp.socialmedia.logic.services.CommentService;
//import com.webapp.socialmedia.logic.services.PostService;
//import com.webapp.socialmedia.logic.services.VoteService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/votes")
//public class VoteController {
//
//    @Autowired
//    private VoteService voteService;
//    @Autowired
//    private PostService postService;
//
//    @PostMapping("/posts/{id}")
//    public String postVote(@RequestBody Vote vote, @PathVariable Long id) {
//        return voteService.postVote(vote, id);
//    }
//
//    @PostMapping("/comments/{id}")
//    public String commentVote(@RequestBody Vote vote, @PathVariable Long id) {
//        return voteService.commentVote(vote, id);
//    }
//
//
//}
