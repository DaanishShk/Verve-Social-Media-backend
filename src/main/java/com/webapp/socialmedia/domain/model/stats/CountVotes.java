package com.webapp.socialmedia.domain.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CountVotes {
    private Long likes;
    private Long dislikes;
}
