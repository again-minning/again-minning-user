package com.example.againminninguser.domain.account.domain.dto.response;

import com.example.againminninguser.domain.account.domain.Account;
import lombok.Data;

@Data
public class LoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(Long id, String email, String nickname, String accessToken, String refreshToken) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
