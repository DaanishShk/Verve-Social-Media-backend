package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT acc FROM Account acc " +
            "LEFT JOIN FETCH acc.friends " +
            "LEFT JOIN FETCH acc.following " +
            "WHERE acc.username = :username ")
    Account findAccountByUsername(@Param("username") String username);

    Optional<Account> findAccountByEmail(String email);

    Long countAccountsByFollowing(Account account);

    Boolean existsAccountByUsername(String username);
}