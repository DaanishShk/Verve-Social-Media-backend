package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.config.exceptions.custom.ApiRequestException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exception")
public class ExceptionTestController {

    @GetMapping("/api")
    public void throwApiException() {
        throw  new ApiRequestException("api request exception thrown");
    }
}
