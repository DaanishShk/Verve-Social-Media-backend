package com.webapp.socialmedia.logic.services;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.CountVotes;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.model.stats.VoteType;
import com.webapp.socialmedia.domain.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AccountService accountService;

    public Vote getVoteByAccount(Account account, Long entityId) {
        return  voteRepository.findVoteByAccountAndEntityId(account, entityId); // if you add new Account() will cause Transient error
    }

    public void postVote(Vote vote, Long id) {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Vote v = voteRepository.findVoteByAccountAndEntityId(account, id);
        if (v != null) {
            v.setType(vote.getType());
            voteRepository.save(v);
            return;
        }

        vote.setAccount(account);
        vote.setEntityId(id);
        voteRepository.save(vote);

        // TODO sql to count total likes
    }

    public void commentVote(Vote vote, Long id) {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Vote v = voteRepository.findVoteByAccountAndEntityId(account, id);

        if(v != null) {
            v.setType(vote.getType());
            voteRepository.save(v);
            return;
        }

        vote.setAccount(account);
        vote.setEntityId(id);
        voteRepository.save(vote);

    }


}
