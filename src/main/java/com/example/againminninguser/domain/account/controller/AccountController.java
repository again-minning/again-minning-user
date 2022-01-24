package com.example.againminninguser.domain.account.controller;

import com.example.againminninguser.domain.account.domain.dto.request.LoginRequest;
import com.example.againminninguser.domain.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        accountService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }
}
