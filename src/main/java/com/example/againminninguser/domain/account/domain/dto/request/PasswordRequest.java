package com.example.againminninguser.domain.account.domain.dto.request;

import lombok.Data;

@Data
public class PasswordRequest {
    private String newPassword;
    private String newPasswordConfirm;
}
