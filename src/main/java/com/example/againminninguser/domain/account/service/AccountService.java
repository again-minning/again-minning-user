package com.example.againminninguser.domain.account.service;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.AccountRepository;
import com.example.againminninguser.domain.account.domain.dto.SignUpDto;
import com.example.againminninguser.domain.account.domain.dto.request.ProfileRequest;
import com.example.againminninguser.domain.account.domain.dto.response.LoginResponse;
import com.example.againminninguser.domain.account.domain.dto.response.ProfileResponse;
import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.global.config.jwt.JwtProvider;
import com.example.againminninguser.global.error.BadRequestException;
import com.example.againminninguser.global.error.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.regex.Pattern;

import static com.example.againminninguser.global.common.content.AccountContent.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$");
    private static final Pattern PASSWORD_REGEX = Pattern.compile("^[a-zA-Z0-9]{8,20}");

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public TokenDto getNewRefresh(String email, String refreshToken) {
        jwtProvider.checkRefreshInRedis(email, refreshToken);
        return jwtProvider.refreshAccessAndRefreshToken(email, Collections.singletonList("ROLE_USER"));
    }

    public LoginResponse login(String email, String password) {
        Account account = accountRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        checkPassword(password, account.getPassword());
        TokenDto tokenDto = jwtProvider.createAccessAndRefreshToken(email, Collections.singletonList("ROLE_USER"));
        jwtProvider.setRefreshInRedis(account.getEmail(), tokenDto.getRefreshToken());
        account.updateLastLogin();
        return LoginResponse.of(account.getId(), account.getEmail(), account.getNickname(), tokenDto);
    }

    private void checkPassword(String password, String encodedPassword) {
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        if(!matches) {
            throw new UserNotFoundException(USER_NOT_FOUND_BY_LOGIN);
        }
    }

    public SignUpDto signUp(SignUpDto signUp) {
        checkDuplicatedEmail(signUp.getEmail());
        validateSignUpRequest(signUp);
        Account account = Account.of(
                signUp.getEmail(),
                passwordEncoder.encode(signUp.getPassword()),
                signUp.getNickname());
        Account savedAccount = accountRepository.save(account);
        return SignUpDto.of(savedAccount.getEmail(), savedAccount.getNickname());
    }

    private void validateSignUpRequest(SignUpDto signUp) {
        checkEmailFormat(signUp.getEmail());
        checkPasswordFormat(signUp.getPassword());
    }

    private void checkEmailFormat(String email) {
        boolean matches = EMAIL_REGEX.matcher(email).matches();
        if(!matches) {
            throw new BadRequestException(INVALID_EMAIL_FORMAT);
        }

    }

    private void checkPasswordFormat(String password) {
        boolean matches = PASSWORD_REGEX.matcher(password).matches();
        if(!matches) {
            throw new BadRequestException(INVALID_PASSWORD_FORMAT);
        }

    }

    private void checkDuplicatedEmail(String email) {
        boolean exists = accountRepository.existsByEmail(email);
        if(exists) {
            throw new BadRequestException(DUPLICATED_EMAIL);
        }
    }

    public void logout(Account account, HttpServletRequest request) {
        jwtProvider.logout(request, account.getEmail());
    }

    public ProfileResponse updateProfile(Account account, ProfileRequest profile) {
        String originalFilename = profile.getProfile().getOriginalFilename();
        /* Todo S3 or GCS
            이미지 저장 정해지면 구현 예쩡
         */
        account.updateProfile(originalFilename);
        accountRepository.save(account);
        return ProfileResponse.from(originalFilename);
    }
}
