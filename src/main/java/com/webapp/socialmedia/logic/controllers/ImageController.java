package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.domain.repositories.ImageRepository;
import com.webapp.socialmedia.logic.services.AccountProfileService;
import com.webapp.socialmedia.logic.services.AccountService;
import com.webapp.socialmedia.logic.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("images/{username}")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping("profilepic")
    public ResponseEntity<byte[]> viewProfilePic(@PathVariable String username) {
        return imageService.viewProfilePicture(username);
    }

    @GetMapping("background")
    public ResponseEntity<byte[]> viewBackground(@PathVariable String username) {
        return imageService.viewBackground(username);
    }

}
