package com.webapp.socialmedia.domain.model.stats;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webapp.socialmedia.domain.model.account.Account;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Vote extends AbstractPersistable<Long> {

    @Enumerated(EnumType.STRING)
    private VoteType type;
    private Long entityId;

    @ManyToOne
    @JsonIgnore
    private Account account;
}
