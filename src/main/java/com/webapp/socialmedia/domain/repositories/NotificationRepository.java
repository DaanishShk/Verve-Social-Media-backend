package com.webapp.socialmedia.domain.repositories;

import com.webapp.socialmedia.domain.model.notifications.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
