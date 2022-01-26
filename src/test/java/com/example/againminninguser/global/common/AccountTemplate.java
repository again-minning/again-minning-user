package com.example.againminninguser.global.common;

import com.example.againminninguser.domain.account.domain.Account;
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
}
