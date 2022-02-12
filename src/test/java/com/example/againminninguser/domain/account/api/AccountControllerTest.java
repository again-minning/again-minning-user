package com.example.againminninguser.domain.account.api;

import com.example.againminninguser.domain.account.controller.AccountController;
import com.example.againminninguser.domain.account.domain.dto.SignUpDto;
import com.example.againminninguser.domain.account.domain.dto.request.PasswordRequest;
import com.example.againminninguser.domain.account.service.AccountService;
import com.example.againminninguser.global.common.AccountTemplate;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.config.account.TestAccount;
import com.example.againminninguser.global.config.jwt.JwtProvider;
import com.example.againminninguser.global.error.BadRequestException;
import com.example.againminninguser.global.error.CustomAuthenticationEntryPoint;
import com.example.againminninguser.global.util.MailUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
@TestPropertySource(properties = { "${spring.jwt.key}=1234" })
@DisplayName("Account Controller 테스트")
public class AccountControllerTest {

    @MockBean
    private AccountService accountService;

    @MockBean
    private JwtProvider jwtProvider;
    @MockBean
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @MockBean
    private MailUtil mailUtil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("잘못된 이메일 형식으로 회원가입 - 회원가입")
    void signUpFailByBadEmail() throws Exception {
        SignUpDto signUpInvalidEmail = AccountTemplate.signUpInvalidEmail;
        given(accountService.signUp(any())).willThrow(new BadRequestException(AccountContent.INVALID_EMAIL_FORMAT));
        String request = objectMapper.writeValueAsString(signUpInvalidEmail);

        ResultActions perform = mockMvc.perform(post("/api/v1/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.message.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message.msg").value(AccountContent.INVALID_EMAIL_FORMAT));
    }

    @Test
    @DisplayName("잘못된 비밀번호 형식으로 회원가입 - 회원가입")
    void signUpFailByBadPassword() throws Exception {
        SignUpDto signUpInvalidPassword = AccountTemplate.signUpInvalidPassword;
        given(accountService.signUp(any())).willThrow(new BadRequestException(AccountContent.INVALID_PASSWORD_FORMAT));
        String request = objectMapper.writeValueAsString(signUpInvalidPassword);

        ResultActions perform = mockMvc.perform(post("/api/v1/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message.msg").value(AccountContent.INVALID_PASSWORD_FORMAT));
    }

    @Test
    @DisplayName("정상 회원가입 - 회원가입")
    void signUpSuccess() throws Exception {
        SignUpDto signUpDto = AccountTemplate.signUp;
        given(accountService.signUp(any())).willReturn(signUpDto);
        String request = objectMapper.writeValueAsString(signUpDto);

        ResultActions perform = mockMvc.perform(post("/api/v1/account/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));

        perform
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message.status").value(HttpStatus.OK.name()))
                .andExpect(jsonPath("$.message.msg").value(AccountContent.SIGN_UP_OK))
                .andExpect(jsonPath("$.data.email").value(signUpDto.getEmail()))
                .andExpect(jsonPath("$.data.nickname").value(signUpDto.getNickname()));
    }

    @Test
    @TestAccount
    @DisplayName("서로 다른 비밀번호 - 비밀번호 변경")
    void updatePasswordFailByNotEqual() throws Exception {
        PasswordRequest badPasswordRequest = AccountTemplate.badPasswordRequest;
        String request = objectMapper.writeValueAsString(badPasswordRequest);
        willThrow(new BadRequestException(AccountContent.PASSWORD_CONFIRM_INVALID))
                .given(accountService).updatePassword(any(), any());
        ResultActions perform = mockMvc.perform(patch("/api/v1/account/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request));
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message.status").value(HttpStatus.BAD_REQUEST.name()))
                .andExpect(jsonPath("$.message.msg").value(AccountContent.PASSWORD_CONFIRM_INVALID));
    }
}
