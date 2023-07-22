package com.webapp.socialmedia.config.exceptions.custom;

public class PrivatePostException extends RuntimeException{

    public PrivatePostException(String message) {
        super(message);
    }

    public PrivatePostException(String message, Throwable cause) {
        super(message, cause);
    }
}
