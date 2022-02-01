package com.example.againminninguser.domain.account.dao;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.account.domain.AccountRepository;
import com.example.againminninguser.global.common.AccountTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Account Repository 테스트")
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    @DisplayName("유저 저장 테스트")
    void saveTest() {
        Account account = AccountTemplate.account;
        Account saved = accountRepository.save(account);
        assertAll(
                () -> assertEquals(account.getEmail(), saved.getEmail()),
                () -> assertEquals(account.getNickname(), saved.getNickname()),
                () -> assertEquals(1L, saved.getId())
        );
    }

    @Test
    @DisplayName("이메일로 유저 조회 테스트")
    void getAccountByEmailTest() {
        Account account = AccountTemplate.account;
        accountRepository.save(account);

        Account accountByEmail = accountRepository.findByEmail(account.getEmail()).get();
        assertAll(
                () -> assertEquals(account.getEmail(), accountByEmail.getEmail()),
                () -> assertEquals(account.getNickname(), accountByEmail.getNickname())
        );
    }

    @Test
    @DisplayName("이메일 존재 유뮤 조회 테스트 - 있는 경우")
    void existAccountByEmailWhenDataIsIt() {
        Account account = AccountTemplate.account;
        accountRepository.save(account);

        boolean exists = accountRepository.existsByEmail(account.getEmail());

        assertTrue(exists);
    }

    @Test
    @DisplayName("이메일 존재 유뮤 조회 테스트 - 없는 경우")
    void existAccountByEmailWhenNoData() {
        Account account = AccountTemplate.account;

        boolean exists = accountRepository.existsByEmail(account.getEmail());

        assertFalse(exists);
    }
}
