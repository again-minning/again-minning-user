package com.example.againminninguser.domain.account.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUp {
    private String email;
    private String password;
    private String nickname;

    public static SignUp of(String email, String password, String nickname) {
        return new SignUp(email, password, nickname);
    }

}
