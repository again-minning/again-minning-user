package com.example.againminninguser.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
@RequiredArgsConstructor
public class MailConfiguration {

    private final JavaMailSender javaMailSender;

    @Bean
    public MimeMessageHelper getMimeMessageHelper() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        return new MimeMessageHelper(mimeMessage, true, "UTF-8");
    }
}
