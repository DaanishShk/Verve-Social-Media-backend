package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String username);

    Optional<Account> findAccountByEmail(String email);

    Long countAccountsByFollowing(Account account);

    Boolean existsAccountByUsername(String username);
}