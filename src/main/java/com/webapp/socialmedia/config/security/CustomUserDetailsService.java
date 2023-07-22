package com.webapp.socialmedia.config.security;


import com.webapp.socialmedia.domain.model.account.Account;
import com.webapp.socialmedia.logic.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Account account = accountService.getByUsername(username);

        if(account == null) {
            throw new UsernameNotFoundException("no account with given username was found");
        }

        return account;
    }


}
