package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {

    AccountProfile findAccountProfileByAccount_Username(String username);

    @Query(value = "SELECT COUNT(*) " +
                    "FROM (SELECT v " +
                    "      FROM post p " +
                    "               JOIN vote v ON p.id = v.entity_id " +
                    "      WHERE p.account_id = :acc AND v.type = 'LIKE' " +
                    "      UNION ALL " +
                    "      SELECT v " +
                    "      FROM comment c " +
                    "               JOIN vote v ON c.id = v.entity_id " +
                    "      WHERE c.account_id = :acc AND v.type = 'LIKE') as qry",
            nativeQuery = true)
    Long countTotalLikesReceived(@Param(value = "acc")Account account);
}