//package com.webapp.socialmedia.config.security.mail;
//
//import com.webapp.socialmedia.domain.model.account.Account;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//public class ActivationToken {
//
//    public ActivationToken(String token, LocalDateTime createdAt, LocalDateTime expiredAt, Account account) {
//        this.token = token;
//        this.createdAt = createdAt;
//        this.expiresAt = expiredAt;
//        this.account = account;
//    }
//
//    @Id
//    @SequenceGenerator(
//            name="confirmation_token_sequence",
//            sequenceName = "confirmation_token_sequence",
//            allocationSize = 1
//    )
//    @GeneratedValue(
//            strategy = GenerationType.SEQUENCE,
//            generator = "confirmation_confirmation_sequence"
//    )
//    private Long id;
//
//    @Column(nullable = false)
//    private String token;
//
//    @Column(nullable = false)
//
//    private LocalDateTime createdAt;
//
//    @Column(nullable = false)
//    private LocalDateTime expiresAt;
//
//    private LocalDateTime confirmedAt;
//
//    @ManyToOne
//    @JoinColumn(
//            nullable = false,
//            name = "account_id"
//    )
//    private Account account;   // One user can have many confirmation tokens
//}
