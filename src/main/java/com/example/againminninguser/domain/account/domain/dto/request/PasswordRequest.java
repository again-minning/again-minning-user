package com.example.againminninguser.domain.account.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordRequest {
    private String newPassword;
    private String newPasswordConfirm;
}
