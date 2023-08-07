package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.CountVotes;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.repositories.CommentRepository;
import com.webapp.socialmedia.logic.events.AccountEvent;
import com.webapp.socialmedia.logic.events.CommentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private AccountEvent accountEvent;
    @Autowired
    private CommentEvent commentEvent;

    public Comment addComment(Comment comment, Long id) {
        comment.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        comment.setAccount(account);
        comment.setCountVotes(new CountVotes());
        comment.setPostId(id);
        this.incrementTotalComments(account);

        commentRepository.save(comment);
        commentEvent.tagUsers(comment, account);
        return comment;
    }

    @Transactional
    @Async
    void incrementTotalComments(Account account) {
        account.getStats().setTotalComments(account.getStats().getTotalComments() + 1);
        accountEvent.commentCountNotification(account);
    }

    public Comment getComment(Long id) {
        return commentRepository.getById(id);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        Account user = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        return commentRepository.findCommentsByPost(postId, user).stream()
                .map(commentDto -> new Comment(
                        commentDto.getComment(),
                        commentDto.getLikes(),
                        commentDto.getDislikes(),
                        commentDto.getUserVoteType()
                )).collect(Collectors.toList());
    }

    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    public String commentVote(Vote vote, Long id) {
        voteService.commentVote(vote, id);
        return "comment voted successfully";
    }

    public List<Comment> getActivityComments(boolean isFriend, String username) {
        Pageable paging = PageRequest.of(0, 5);
        if (isFriend || username.equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            return commentRepository.findCommentsByAccount_UsernameOrderByTimestampDesc(username, paging).getContent();
        }
        return commentRepository.findCommentsByPostVisibilityPublic(username, paging).getContent();
    }
}
