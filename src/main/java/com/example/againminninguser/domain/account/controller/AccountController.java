package com.example.againminninguser.domain.account.controller;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.dto.request.LoginRequest;
import com.example.againminninguser.domain.account.domain.dto.SignUpDto;
import com.example.againminninguser.domain.account.domain.dto.request.ProfileRequest;
import com.example.againminninguser.domain.account.domain.dto.response.LoginResponse;
import com.example.againminninguser.domain.account.domain.dto.response.ProfileResponse;
import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.domain.account.service.AccountService;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import com.example.againminninguser.global.util.AuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PatchMapping("/profile")
    public CustomResponseEntity<ProfileResponse> updateProfile(@AuthAccount Account account, ProfileRequest profile) {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.PROFILE_UPDATE_OK),
                accountService.updateProfile(account, profile)
        );
    }

    @GetMapping("/logout")
    public CustomResponseEntity<Message> logout(@AuthAccount Account account, HttpServletRequest request) {
        accountService.logout(account, request);
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.LOGOUT_OK)
        );
    }

    @PostMapping("/")
    public CustomResponseEntity<SignUpDto> signUp(@RequestBody SignUpDto signUp) {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.SIGN_UP_OK),
                accountService.signUp(signUp)
        );
    }

    @PostMapping("/login")
    public CustomResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.LOGIN_OK),
                accountService.login(loginRequest.getEmail(), loginRequest.getPassword())
        );
    }

    @GetMapping("/refresh")
    public CustomResponseEntity<TokenDto> refreshToken(
            @PathParam("email") String email, @PathParam("refreshToken") String refreshToken) {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.REFRESH_OK),
                accountService.getNewRefresh(email, refreshToken)
        );
    }
}
