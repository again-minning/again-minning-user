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

    /* Todo Add Validation */
    private String email;

    /* Todo Add Validation */
    private String password;

    /* Todo Add Validation */
    private String nickname;

    private String profile;

    private boolean isAlarm;

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
}
