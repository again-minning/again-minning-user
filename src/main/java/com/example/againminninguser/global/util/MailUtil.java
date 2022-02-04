package com.example.againminninguser.global.util;

import com.example.againminninguser.domain.account.domain.dto.response.TokenDto;
import com.example.againminninguser.global.config.jwt.JwtProvider;
import com.example.againminninguser.global.error.BadRequestException;
import com.example.againminninguser.global.error.ServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static com.example.againminninguser.global.common.content.MailContent.*;

@Service
@RequiredArgsConstructor
public class MailUtil {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;
    private final Random random;
    private final MimeMessageHelper messageHelper;
    private final JwtProvider jwtProvider;

    private String getAuthCode() {
        String authKey = "";
        for(int i = 0; i < 6; i++) {
            int num = random.nextInt(9);
            authKey += String.valueOf(num);
        }
        return authKey;
    }

    public void sendAuthMail(String email) {
        String authKey = this.getAuthCode();
        String text = "<h1>[이메일 인증]</h1>" +
                "<p>아래의 인증번호를 입력하여 인증을 진행하세요.</p>"
                + "<p>인증번호는 3분간 유효합니다.</p>"
                + "인증번호 = " + authKey;
        try {
            messageHelper.setSubject("비밀번호 변경 재설정 인증메일");
            messageHelper.setText(text, true);
            messageHelper.setTo(email);
            mailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException  e) {
            throw new ServerErrorException(SEND_MAIL_FAIL);
        }
        redisTemplate.opsForValue().set("AuthKey_" + email, authKey, Duration.ofMillis( 3 * 60 * 1000L ));
    }

    public TokenDto confirmAuthKey(String email, String authKey) {
        String authKeyInRedis = redisTemplate.opsForValue().get("AuthKey_" + email);
        if(Objects.isNull(authKeyInRedis)) {
            throw new BadRequestException(EXPIRED_AUTH_KEY);
        }
        boolean result = authKey.equals(authKeyInRedis);
        if(!result) {
            throw new BadRequestException(AUTH_KEY_NOT_EQUAL);
        }
        redisTemplate.delete("AuthKey_" + email);
        String tempToken = jwtProvider.createToken(email, List.of(""), 3 * 60 * 1000L);
        return TokenDto.of(tempToken, null);
    }
}
