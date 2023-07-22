package com.webapp.socialmedia.domain.model.comment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.CountVotes;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.model.stats.VoteType;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment extends AbstractPersistable<Long> {

    public Comment(Comment comment, Long likes, Long dislikes, int userVoteType) {
        super.setId(comment.getId());
        this.content = comment.getContent();
        this.countVotes = new CountVotes(likes, dislikes);
        this.timestamp = comment.getTimestamp();
        this.account = comment.getAccount();
        this.postId = comment.getPostId();
        this.userVoteType = userVoteType == 1? VoteType.LIKE: userVoteType == -1? VoteType.DISLIKE: VoteType.NONE;
    }

    @Column(length = 500)
    private String content;

//    @OneToMany
//    @JsonIgnore
//    private List<Vote> votes;

    @Transient
    private CountVotes countVotes;
    @Transient
    private VoteType userVoteType;

    private LocalDateTime timestamp;

    @ManyToOne
    private Account account;

    private Long postId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private Post post;
}
