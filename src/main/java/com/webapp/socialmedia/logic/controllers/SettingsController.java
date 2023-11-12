package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.config.security.jwt.JwtUtil;
import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.domain.model.image.Image;
import com.webapp.socialmedia.config.security.jwt.jwt.JwtResponse;
import com.webapp.socialmedia.domain.model.account.settings.ProfileCard;
import com.webapp.socialmedia.domain.model.account.settings.SecurityBasic;
import com.webapp.socialmedia.domain.model.account.settings.SecurityPassword;
import com.webapp.socialmedia.logic.services.AccountProfileService;
import com.webapp.socialmedia.logic.services.AccountService;
import com.webapp.socialmedia.logic.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/settings")
public class SettingsController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountProfileService accountProfileService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/general")
    private Account general(@RequestPart String displayName,
                            @RequestPart(name = "file") MultipartFile image)
            throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountService.getByUsername(username);
        account.setDisplayName(displayName);

//        Image im = account.getProfilePic();
        Image im = imageService.getProfilePicture(username);
        if (im == null) im = new Image();
        im.setContent(image.getBytes());
        im.setMediaType(image.getContentType());
        imageService.saveImage(im, account.getUsername());
        account.setProfilePic(im);

        accountService.saveAccount(account);

        return account;
    }

    @PostMapping("security/basic")
    public JwtResponse securityBasic(@RequestBody SecurityBasic basic) {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (!passwordEncoder.matches(basic.getPassword(), account.getPassword()))
            throw new BadCredentialsException("Password was incorrect");

        if (basic.getUsername() != null) account.setUsername(basic.getUsername());
        if (basic.getEmail() != null) account.setEmail(basic.getEmail());

        accountService.saveAccount(account);

        AccountProfile profile = accountProfileService.getProfileFromUsername(basic.getUsername());
        return new JwtResponse(account, profile, jwtUtil.generateToken(account));
    }

    @PostMapping("security/password")
    public String securityPassword(@RequestBody SecurityPassword security) {
        Account account = accountService.getByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(security);
        if (!passwordEncoder.matches(security.getPassword(), account.getPassword()))
            throw new BadCredentialsException("Password was incorrect");

        if (security.getNewPassword().equals(security.getConfirmPassword()))
            account.setPassword(passwordEncoder.encode(security.getNewPassword()));
        accountService.saveAccount(account);

        return "password updation successful";
    }

    @PostMapping("/profile/card")
    public AccountProfile profileCard(@RequestBody ProfileCard card) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountProfile profile = accountProfileService.getProfileFromUsername(username);

        profile.setDescription(card.getDescription());
        profile.setLinks(card.getLinks());

        accountProfileService.saveProfile(profile);

        return profile;
    }

    @PostMapping("profile/main")
    public AccountProfile profileMain(@RequestPart String aboutMe, @RequestPart MultipartFile background) throws IOException {
        AccountProfile profile = accountProfileService.getProfileFromUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        profile.setAboutMe(aboutMe);
        Image image = new Image(background.getBytes(), background.getContentType());
        imageService.saveImage(image, profile.getAccount().getUsername());
        profile.setBackground(image);
        accountProfileService.saveProfile(profile);
        return profile;
    }
}
