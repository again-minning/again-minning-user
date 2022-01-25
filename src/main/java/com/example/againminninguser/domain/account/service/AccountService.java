package com.example.againminninguser.domain.account.service;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.AccountRepository;
import com.example.againminninguser.domain.account.domain.dto.response.LoginResponse;
import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.global.config.jwt.JwtProvider;
import com.example.againminninguser.global.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static com.example.againminninguser.global.common.content.AccountContent.USER_NOT_FOUND_BY_LOGIN;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenDto getNewRefresh(String accountEmail, String refreshToken) {
        jwtProvider.checkRefreshInRedis(accountEmail, refreshToken);
        String newAccessToken = jwtProvider.createAccessToken(accountEmail, Collections.singletonList("ROLE_USER"));
        String newRefreshToken = jwtProvider.createRefreshToken(accountEmail, Collections.singletonList("ROLE_USER"));
        jwtProvider.changeRefreshTokenInRedis(accountEmail, newRefreshToken);
        return new TokenDto(newAccessToken, newRefreshToken);
    }

    @Transactional
    public LoginResponse login(String email, String password) {
        Account account = accountRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        checkPassword(password, account.getPassword());
        String accessToken = jwtProvider.createAccessToken(account.getEmail(), Collections.singletonList("ROLE_USER"));
        String refreshToken = jwtProvider.createRefreshToken(account.getEmail(), Collections.singletonList("ROLE_USER"));
        jwtProvider.setRefreshInRedis(account.getEmail(), refreshToken);
        account.updateLastLogin();
        return new LoginResponse(
                account.getId(), account.getEmail(), account.getNickname(), accessToken, refreshToken);

    }

    private void checkPassword(String password, String encodedPassword) {
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        if(!matches) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_LOGIN);
        }
    }

}
