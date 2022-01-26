package com.example.againminninguser.global.config.jwt;

import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.domain.account.service.CustomUserDetailsService;
import com.example.againminninguser.global.common.content.AccountContent;
import com.example.againminninguser.global.error.RefreshTokenBadRequestException;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.key}")
    private String secretKey;

    private final CustomUserDetailsService customUserDetailsService;
    private final RedisTemplate<String, String> redisTemplate;

    // 의존성 주입 후, 초기화를 수행
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public TokenDto refreshAccessAndRefreshToken(String email, List<String> roles) {
        TokenDto tokenDto = this.createAccessAndRefreshToken(email, roles);
        this.changeRefreshTokenInRedis(email, tokenDto.getRefreshToken());
        return tokenDto;
    }

    public TokenDto createAccessAndRefreshToken(String email, List<String> roles) {
        String accessToken = createAccessToken(email, roles);
        String refreshToken = createRefreshToken(email, roles);
        return TokenDto.of(accessToken, refreshToken);
    }

    public String createAccessToken(String email, List<String> roles){
        // 1h
        long accessTokenValidTime = 1000L;
        return this.createToken(email, roles, accessTokenValidTime);
    }

    public String createRefreshToken(String email, List<String> roles) {
        // 3day
        long refreshTokenValidTime = 3 * 24 * 60 * 60 * 1000L;
        return this.createToken(email, roles, refreshTokenValidTime);
    }

    public String createToken(String email, List<String> roles, long tokenValid) {
        Claims claims = Jwts.claims().setSubject(email); // claims 생성 및 payload 설정
        claims.put("roles", roles); // 권한 설정, key/ value 쌍으로 저장

        Date date = new Date();
        return Jwts.builder()
                .setClaims(claims) // 발행 유저 정보 저장
                .setIssuedAt(date) // 발행 시간 저장
                .setExpiration(new Date(date.getTime() + tokenValid)) // 토큰 유효 시간 저장
                .signWith(SignatureAlgorithm.HS256, secretKey) // 해싱 알고리즘 및 키 설정
                .compact(); // 생성
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getUserEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Date getExpiration(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    public String getUserEmail(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token != null
                ? request.getHeader("Authorization").substring(7)
                : null;
    }

    public boolean validateToken(HttpServletRequest request, String token) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            Optional<String> isBlackList = Optional.ofNullable(operations.get(token));
            if(isBlackList.isPresent()) {
                throw new UsernameNotFoundException(AccountContent.EXPIRED_TOKEN);// 로그아웃 된 토큰요청 예외
            }
            return !claims.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException | UsernameNotFoundException e) {
            request.setAttribute("exception", AccountContent.EXPIRED_TOKEN);
            System.out.println("asd");
            return false;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", AccountContent.MALFORMED_TOKEN);
            return false;
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", AccountContent.EMPTY_TOKEN);
            return false;
        }
    }

    public void setRefreshInRedis(String email, String refreshToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        long expiration = this.getExpiration(refreshToken).getTime() - System.currentTimeMillis();
        values.set(email, refreshToken, Duration.ofMillis(expiration));
    }

    public void checkRefreshInRedis(String accountEmail, String refreshToken) {
        ValueOperations<String, String> values = redisTemplate.opsForValue();
        String refreshTokenInRedis = values.get(accountEmail);
        if(!refreshToken.equals(refreshTokenInRedis)) {
            throw new RefreshTokenBadRequestException();
        }
    }

    public void changeRefreshTokenInRedis(String accountEmail, String newRefreshToken) {
        redisTemplate.delete(accountEmail);
        this.setRefreshInRedis(accountEmail, newRefreshToken);
    }
}