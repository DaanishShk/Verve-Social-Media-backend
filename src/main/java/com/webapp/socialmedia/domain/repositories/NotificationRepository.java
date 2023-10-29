package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.notifications.Notification;
import com.webapp.socialmedia.domain.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    public List<Notification> findByOwnerAccountOrderByTimestampDesc(Account ownerAccount); //TODO use join fetch or else N+1 problem

    public Long countNotificationsByOwnerAccountAndViewedIsFalse(Account ownerAccount);

    public void deleteNotificationsByPost(Post post);
}
