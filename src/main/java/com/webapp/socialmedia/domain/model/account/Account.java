package com.webapp.socialmedia.domain.model.account;

import com.fasterxml.jackson.annotation.*;
import com.webapp.socialmedia.domain.model.account.security.UserRoles;
import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.model.post.Post;
import com.webapp.socialmedia.domain.model.stats.ProfileStats;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account extends AbstractPersistable<Long> implements UserDetails {              // might break certain parts of application

    @Column(length = 30)
    private String username;
//    @JsonIgnore  was ignoring property read as well
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;    // Bcrypt generates long password hash
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

//    @JsonIgnore
//    @OneToOne   // can get account info when go to profile
//    private AccountProfile accountProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Image profilePic;
    private String displayName;


//    @OneToMany(fetch = FetchType.LAZY)
////    @JsonIgnore
////    @JsonBackReference
//    private List<Post> posts;        // was causing infinite recursion
    @Embedded
    @JsonIgnore
    private ProfileStats stats;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference("friends")
    private Set<Account> friends;
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonBackReference("following")
    private Set<Account> following;     // check how spring creates a contains method (by id maybe?)

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private Set<UserRoles> roles;
//    @JsonIgnore
//    private boolean enabled = false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRoles role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.name()));
        }
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
