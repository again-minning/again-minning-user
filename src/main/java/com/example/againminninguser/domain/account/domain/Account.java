package com.example.againminninguser.domain.account.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
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

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate lastLogin;
}
