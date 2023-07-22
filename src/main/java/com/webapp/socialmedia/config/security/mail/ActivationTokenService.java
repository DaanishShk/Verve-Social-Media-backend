//package com.webapp.socialmedia.config.security.mail;
//
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.time.ZoneOffset;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class ActivationTokenService {
//
//    private final ActivationTokenRepository confirmationTokenRepository;
//
//    public void saveConfirmationToken(ActivationToken token) {
//        confirmationTokenRepository.save(token);
//    }
//
//    public Optional<ActivationToken> getToken(String token) {
//        return confirmationTokenRepository.findByToken(token);
//    }
//
//    public void setConfirmedAt(ActivationToken token) {
//        token.setConfirmedAt(LocalDateTime.now(ZoneOffset.UTC));
//        confirmationTokenRepository.save(token);
//    }
//}
