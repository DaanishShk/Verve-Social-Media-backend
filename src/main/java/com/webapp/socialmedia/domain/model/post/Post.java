package com.webapp.socialmedia.domain.model.post;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.webapp.socialmedia.domain.model.comment.Comment;
import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.stats.CountVotes;
import com.webapp.socialmedia.domain.model.stats.Vote;
import com.webapp.socialmedia.domain.model.stats.VoteType;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@JsonIdentityInfo(
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Post implements Serializable {

    public Post(Post post, Long likes, Long dislikes, int userVoteType, List<Comment> comments, Long commentsLength) {
        this.id = post.getId();
        this.account = post.getAccount();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.comments = comments;
        this.commentsLength = commentsLength;
        this.visibility = post.getVisibility();
        this.type = post.getType();
        this.timestamp = post.getTimestamp();
        this.countVotes = new CountVotes(likes, dislikes);
        this.userVoteType = userVoteType == 1? VoteType.LIKE: userVoteType == -1? VoteType.DISLIKE: VoteType.NONE;
    }

    public Post(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    @Enumerated(EnumType.STRING)
    private PostType type;

    @Column(length = 4000)
    private String content;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;
    private LocalDateTime timestamp;

//    @Embedded
//    private Dimensions dimensions;

    // todo: list of users that like or dislike
//    @OneToMany
//    @JsonIgnore
//    private List<Vote> votes;

    @Transient
    private CountVotes countVotes;
    @Transient
    private VoteType userVoteType;
    @Transient
    private Long commentsLength;

    @ManyToOne
    // without this causing infinite recursion @JsonManagedReference causing mediatype error
    private Account account;

//    @OneToMany(fetch = FetchType.LAZY)
    @Transient
    private List<Comment> comments;

}
