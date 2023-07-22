package com.webapp.socialmedia.domain.model.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileStats {
    private Long totalLikes;
    private Long totalPosts;
    private Long totalComments;
}
