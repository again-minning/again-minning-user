package com.example.againminninguser.domain.account.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;

    public static SignUpDto of(String email, String password, String nickname) {
        return new SignUpDto(email, password, nickname);
    }

}
