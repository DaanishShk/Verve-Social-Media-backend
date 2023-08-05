package com.webapp.socialmedia.domain.model.notifications;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification extends AbstractPersistable<Long> {

    private String message;
    private LocalDateTime timestamp;
    private boolean viewed;
    @OneToOne
    private Account account;
    @OneToOne
    private Post post;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
}
