package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.logic.services.AccountService;
import com.webapp.socialmedia.logic.services.CommentService;
import com.webapp.socialmedia.logic.services.PostService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/{id}/comments")
    public Comment addComment(@RequestBody Comment comment, @PathVariable Long id) { // using comment was giving mediatype error
        return commentService.addComment(comment, id);
        //        System.out.println(comment);  account post causes infinite recursion
    }

    @GetMapping("/posts/{id}/comments/test")
    public List<Comment> testJPQL(@PathVariable Long id) {
        return commentService.getCommentsByPostId(id);
    }

    @PostMapping("/comments/{id}/votes")
    public String commentVote(@RequestBody Vote vote, @PathVariable Long id) {
        return commentService.commentVote(vote, id);
    }
}
