package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByOwnerAccount(Account ownerAccount);

    public Long countNotificationsByOwnerAccountAndViewedIsFalse(Account ownerAccount);
}
