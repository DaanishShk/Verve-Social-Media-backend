package com.webapp.socialmedia.config.exceptions.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApiExceptionDetails {
    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime timeStamp;
}
