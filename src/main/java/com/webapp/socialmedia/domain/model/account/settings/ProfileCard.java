package com.webapp.socialmedia.domain.model.account.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCard {
    private String description;
    private Map<String, String> links;
}
