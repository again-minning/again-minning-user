package com.example.againminninguser.domain.account.domain.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
