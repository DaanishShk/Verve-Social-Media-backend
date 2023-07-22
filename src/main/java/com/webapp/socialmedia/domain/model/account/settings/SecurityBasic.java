package com.webapp.socialmedia.domain.model.account.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityBasic {
    private String username;
    private String password;
    private String email;
}
