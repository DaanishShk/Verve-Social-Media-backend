package com.webapp.socialmedia.domain.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileStats {
    @Transient
    private Long totalLikes;
    private Long totalPosts;
    private Long totalComments;     // TODO remove columns from entity, change to query
}
