package com.example.againminninguser.domain.account;

import com.example.againminninguser.domain.account.domain.AccountRepository;
import com.example.againminninguser.domain.account.service.AccountService;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.error.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Test
    @DisplayName("중복 이메일 검증 텍스트")
    void duplicatedEmailTest() {
        String email = "test@test.com";
        given(accountRepository.existsByEmail(email)).willReturn(true);

        String errorMessage =
                assertThrows(BadRequestException.class,
                () -> ReflectionTestUtils.invokeMethod(accountService, "checkDuplicatedEmail", email)).getMessage();
        assertEquals(AccountContent.DUPLICATED_EMAIL, errorMessage);

    }

    @Test
    @DisplayName("이메일 형식 검증 테스트")
    void emailValidationTest() {
        String email = "test";

        String errorMessage =
                assertThrows(BadRequestException.class,
                () -> ReflectionTestUtils.invokeMethod(accountService, "checkEmailFormat", email)).getMessage();
        assertEquals(AccountContent.INVALID_EMAIL_FORMAT, errorMessage);
    }

    @Test
    @DisplayName("비밀번호 형식 검증 테스트")
    void passwordValidationTest() {
        String password = "1234567";

        String errorMessage =
                assertThrows(BadRequestException.class,
                        () -> ReflectionTestUtils.invokeMethod(accountService, "checkPasswordFormat", password)).getMessage();
        assertEquals(AccountContent.INVALID_PASSWORD_FORMAT, errorMessage);

    }
}
