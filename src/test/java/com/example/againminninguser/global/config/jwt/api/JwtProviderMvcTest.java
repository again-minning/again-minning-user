package com.example.againminninguser.global.config.jwt.api;

import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.config.jwt.JwtProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = { "${spring.jwt.key}=1234" })
@DisplayName("JwtProvider MVC(Validation) Test")
public class JwtProviderMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("만료된 토큰으로 요청하기")
    void expiredTokenTest() throws Exception {
        String token = "bearer " + jwtProvider.createToken("test@test.com", List.of("USER_ROLE"), 1);
        ResultActions result = mockMvc.perform(
                get("/")
                        .header("Authorization", token));

        result
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.name()))
                .andExpect(jsonPath("$.msg").value(AccountContent.EXPIRED_TOKEN));
    }

    @Test
    @DisplayName("토큰없이 요청하기")
    void nullTokenTest() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/"));

        result
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.name()))
                .andExpect(jsonPath("$.msg").value(AccountContent.EMPTY_TOKEN));
    }

    @Test
    @DisplayName("잘못된 토큰으로 요청하기")
    void malformedTokenTest() throws Exception {
        String token = "lalalalala";
        ResultActions result = mockMvc.perform(
                get("/")
                        .header("Authorization", token));

        result
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(HttpStatus.FORBIDDEN.name()))
                .andExpect(jsonPath("$.msg").value(AccountContent.MALFORMED_TOKEN));
    }
}
