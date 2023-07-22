package com.webapp.socialmedia.domain.model.account;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.model.post.Post;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountProfile extends AbstractPersistable<Long> {

    public AccountProfile(Account account) {
        this.account = account;
        this.description = "Add a short description here";
        this.aboutMe = "Tell others who visit your profile about yourself here";
        this.links = new HashMap<>();
    }

    @OneToOne
    private Account account;
    @Column(length = 100)
    private String description;
    @Column(length = 1000)
    private String aboutMe;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Image background;


    @ElementCollection
    private Map<String, String> links;

    @Transient
    private Long numberFollowing;
    @Transient
    private Long numberFollowers;
}

// Also causing some runtime issue