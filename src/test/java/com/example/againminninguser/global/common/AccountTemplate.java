package com.example.againminninguser.global.common;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.dto.SignUpDto;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AccountTemplate {

    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static final Account account =
            Account.builder()
                    .email("test@test.com")
                    .password(passwordEncoder.encode("12345678"))
                    .nickname("test")
                    .build();

    public static final SignUpDto signUp =
            SignUpDto.of("test@test.com", "12345678", "sol");

    public static final SignUpDto signUpInvalidEmail =
            SignUpDto.of("test", "12345678", "sol");

    public static final SignUpDto signUpInvalidPassword =
            SignUpDto.of("test@test.com", "1234567", "sol");
}
