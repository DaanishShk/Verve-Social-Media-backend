//package com.webapp.socialmedia.logic.controllers;
//
//import com.webapp.socialmedia.domain.model.account.Account;
//import com.webapp.socialmedia.logic.services.ActivationService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping(path = "/accounts/activation")
//public class ActivationController {
//
//    @Autowired
//    private ActivationService activationService;
//
//    @GetMapping
//    public String confirm(@RequestParam("token") String token) {
//        return activationService.confirmToken(token);
//    }
//}
