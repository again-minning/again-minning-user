package com.example.againminninguser.domain.account.domain.dto.response;

import lombok.Data;

@Data
public class ProfileResponse {
    private String profile;
    private ProfileResponse(String url) {
        this.profile = url;
    }
    public static ProfileResponse from(String url) {
        return new ProfileResponse(url);
    }
}
