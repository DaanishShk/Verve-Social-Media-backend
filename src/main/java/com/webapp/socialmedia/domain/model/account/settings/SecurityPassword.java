package com.webapp.socialmedia.domain.model.account.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityPassword {
    private String newPassword;
    private String confirmPassword;
    private String password;
}
