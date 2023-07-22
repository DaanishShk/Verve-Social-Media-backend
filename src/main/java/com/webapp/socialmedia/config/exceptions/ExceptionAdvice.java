package com.webapp.socialmedia.config.exceptions;

import com.webapp.socialmedia.config.exceptions.custom.ApiRequestException;
import com.webapp.socialmedia.config.exceptions.custom.PrivatePostException;
import com.webapp.socialmedia.config.exceptions.exception.ApiExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        ApiExceptionDetails exceptionDetails = new ApiExceptionDetails(e.getMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {AuthenticationException.class})
    public ResponseEntity<Object> handleAuthException(AuthenticationException e) {
        ApiExceptionDetails exceptionDetails = new ApiExceptionDetails(e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {PrivatePostException.class})
    public ResponseEntity<Object> handlePrivatePostException(PrivatePostException e) {
        ApiExceptionDetails exceptionDetails = new ApiExceptionDetails(e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(value = {UsernameNotFoundException.class})
//    public ResponseEntity<Object> handleAuthException(UsernameNotFoundException e) {
//        ApiExceptionDetails exceptionDetails = new ApiExceptionDetails(e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
//        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(value = {BadCredentialsException.class})
//    public ResponseEntity<Object> handleAuthException(BadCredentialsException e) {
//        ApiExceptionDetails exceptionDetails = new ApiExceptionDetails(e.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
//        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
//    }
}
