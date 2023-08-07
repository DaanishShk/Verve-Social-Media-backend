package com.webapp.socialmedia.domain.model.account.relations;

import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.logic.events.FriendRequestListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(FriendRequestListener.class)
public class FriendRequest extends AbstractPersistable<Long> {

    @OneToOne
    private Account sender;
    @OneToOne
    private Account receiver;

    private LocalDateTime timestamp;
    @Transient
    private FriendRequestState requestState;
}
