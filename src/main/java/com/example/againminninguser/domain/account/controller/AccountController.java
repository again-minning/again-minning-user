package com.example.againminninguser.domain.account.controller;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.dto.request.LoginRequest;
import com.example.againminninguser.domain.account.domain.dto.SignUpDto;
import com.example.againminninguser.domain.account.domain.dto.request.PasswordRequest;
import com.example.againminninguser.domain.account.domain.dto.request.ProfileRequest;
import com.example.againminninguser.domain.account.domain.dto.response.LoginResponse;
import com.example.againminninguser.domain.account.domain.dto.response.ProfileResponse;
import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.domain.account.service.AccountService;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import com.example.againminninguser.global.util.AuthAccount;
import com.example.againminninguser.global.util.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import static com.example.againminninguser.global.common.content.MailContent.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final MailUtil mailUtil;

    @GetMapping("/confirm")
    public CustomResponseEntity<TokenDto> confirmAuthKey(@PathParam("email") String email, @PathParam("authKey") String authKey) {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AUTH_KEY_OK),
                mailUtil.confirmAuthKey(email, authKey)
        );
    }

    @GetMapping("/mail")
    public CustomResponseEntity<Message> sendMail(@PathParam("email") String email) {
        mailUtil.sendAuthMail(email);
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, EMAIL_SEND_OK)
        );
    }

    @PatchMapping("/password")
    public CustomResponseEntity<Message> updatePassword(@AuthAccount Account account, @RequestBody PasswordRequest passwordRequest) {
        accountService.updatePassword(account, passwordRequest);
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, AccountContent.PASSWORD_UPDATE_OK)
        );
    }

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
