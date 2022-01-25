package com.example.againminninguser.global.config.jwt.service;

import com.example.againminninguser.global.config.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(properties = { "${spring.jwt.key}=1234" })
@ExtendWith({MockitoExtension.class})
@DisplayName("JwtProvider Service Test")
public class JwtProviderTest {

    @InjectMocks
    private JwtProvider jwtProvider;

    @Mock
    private HttpServletRequest request;

    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private ValueOperations<String, String> operations;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(jwtProvider, "secretKey", "1234");
    }

    @Test
    @DisplayName("createToken() 테스트")
    void createTokenTest() {
        //given
        String email = "test@test.com";
        List<String> roles = List.of("ROLE_USER");
        long validTime = 1000 * 6L;

        // when
        String token = jwtProvider.createToken(email, roles, validTime);

        // then
        assertNotNull(token);
    }

    @Test
    @DisplayName("getUserEmail() 테스트")
    void getUserEmailTest() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of("ROLE_USER");
        long validTime = 1000 * 6L;
        String token = jwtProvider.createToken(email, roles, validTime);

        // when
        String emailByToken = jwtProvider.getUserEmail(token);

        // then
        assertEquals(email, emailByToken);
    }

    @Test
    @DisplayName("getAccessTokenFromHeader() 테스트")
    void getAccessTokenFromHeader() {
        // given
        String mockToken = "Bearer ey...";
        BDDMockito.given(request.getHeader("Authorization")).willReturn(mockToken);

        // when
        String accessToken = jwtProvider.getAccessTokenFromHeader(request);

        // then
        assertEquals("ey...", accessToken);
    }

    @Test
    @DisplayName("validateToken() 정상 토큰 테스트")
    void validateTokenSuccessTest() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of("ROLE_USER");
        long validTime = 1000 * 6L;
        String token = jwtProvider.createToken(email, roles, validTime);
        BDDMockito.given(redisTemplate.opsForValue()).willReturn(operations);
        BDDMockito.given(operations.get(token)).willReturn(null);

        // when
        boolean result = jwtProvider.validateToken(request, token);

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("validateToken() 만료된 토큰 테스트")
    void validateTokenFailTestByExpired() {
        // given
        String email = "test@test.com";
        List<String> roles = List.of("ROLE_USER");
        long validTime = 1L;
        String token = jwtProvider.createToken(email, roles, validTime);
        BDDMockito.given(redisTemplate.opsForValue()).willReturn(operations);

        // when
        boolean result = jwtProvider.validateToken(request, token);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateToken() 잘못된 형식 토큰 테스트")
    void validateTokenFailTestByMalformed() {
        // given
        String token = "eyJhbGciOiJIUzI1NiJ9.";
        BDDMockito.given(redisTemplate.opsForValue()).willReturn(operations);

        // when
        boolean result = jwtProvider.validateToken(request, token);

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("validateToken() 빈 토큰 테스트")
    void validateTokenFailTestByNull() {
        // given
        String token = "";
        BDDMockito.given(redisTemplate.opsForValue()).willReturn(operations);

        // when
        boolean result = jwtProvider.validateToken(request, token);

        // then
        assertFalse(result);
    }

}
