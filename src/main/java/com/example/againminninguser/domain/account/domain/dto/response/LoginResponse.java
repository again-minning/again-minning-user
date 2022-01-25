package com.example.againminninguser.domain.account.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String email;
    private String nickname;
    private String accessToken;
    private String refreshToken;

    public static LoginResponse of(Long id, String email, String nickname, TokenDto tokenDto) {
        return new LoginResponse(id, email, nickname, tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }
}
