package com.example.againminninguser.domain.account.service;

import com.example.againminninguser.domain.account.domain.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    public void login(String id, String password) {
        // Todo Login
    }

}
