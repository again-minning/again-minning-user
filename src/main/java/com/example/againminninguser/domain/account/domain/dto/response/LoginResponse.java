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

    public LoginResponse(Account account, String accessToken, String refreshToken) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.nickname = account.getNickname();
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
