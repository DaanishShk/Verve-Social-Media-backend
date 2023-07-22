package com.webapp.socialmedia.domain.model.image;

import com.webapp.socialmedia.domain.model.account.Account;
import lombok.*;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Image extends AbstractPersistable<Long> {

//    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    @Basic(fetch = FetchType.LAZY)// check why annotation is deprecated
    private byte[] content;

//    @OneToOne(mappedBy = "profilePic")
//    private Account acount;

    private String mediaType;
}
