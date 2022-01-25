package com.example.againminninguser.domain.account.domain.dto.response;

import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(Long id, String email, String nickname, TokenDto tokenDto) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.accessToken = tokenDto.getAccessToken();
        this.refreshToken = tokenDto.getRefreshToken();
    }
}
