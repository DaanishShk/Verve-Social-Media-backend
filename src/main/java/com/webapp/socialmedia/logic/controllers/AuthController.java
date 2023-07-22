package com.webapp.socialmedia.logic.controllers;

import com.webapp.socialmedia.config.security.jwt.JwtUtil;
import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.domain.model.account.AccountProfile;
import com.webapp.socialmedia.config.security.jwt.jwt.JwtRequest;
import com.webapp.socialmedia.config.security.jwt.jwt.JwtResponse;
import com.webapp.socialmedia.logic.services.AccountProfileService;
import com.webapp.socialmedia.logic.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountProfileService accountProfileService;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{

        Account account = accountService.getByUsername(jwtRequest.getUsername());
        if(account == null) {
            throw new UsernameNotFoundException("Username not found");
        }


        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new BadCredentialsException("Password does not match");
        }

        // user exists, changed from userDetails to account for frontend data

        String token = this.jwtUtil.generateToken(account);

        AccountProfile profile = accountProfileService.getProfileFromUsername(account.getUsername());
        if(profile == null) {
            profile = new AccountProfile();
            profile.setAccount(account);
            accountProfileService.saveProfile(profile);
        }

        return ResponseEntity.ok(new JwtResponse(account, profile, token)); // Response with HTTP 200 status
    }
}
