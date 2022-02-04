package com.example.againminninguser.domain.account.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String profile;

    private boolean isAlarm;

    private boolean isQuote;

    private String fcmToken;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime lastLogin;

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public static Account of(String email, String password, String nickname) {
        return Account.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .isAlarm(false)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void updateProfile(String url) {
        this.profile = url;
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
  
    public void changeIsQuoteOfStatus() {
        this.isQuote = !this.isQuote;
    }
}
