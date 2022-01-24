package com.example.againminninguser.domain.account.controller;

import com.example.againminninguser.domain.account.domain.dto.request.LoginRequest;
import com.example.againminninguser.domain.account.domain.dto.response.LoginResponse;
import com.example.againminninguser.domain.account.service.AccountService;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public CustomResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new CustomResponseEntity<>(
                Message.builder().msg(AccountContent.LOGIN_OK).status(HttpStatus.OK).build(),
                accountService.login(loginRequest.getEmail(), loginRequest.getPassword())
        );
    }
}
