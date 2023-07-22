package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.model.stats.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Vote findVoteByAccountAndEntityId(Account account, Long entityId);


}