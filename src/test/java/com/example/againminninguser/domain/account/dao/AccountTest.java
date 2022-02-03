package com.example.againminninguser.domain.account.dao;

import com.example.againminninguser.domain.account.domain.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AccountTest {


    @Test
    @DisplayName("updateProfile 테스트")
    void updateProfileTest() {
        LocalDateTime minusHours = LocalDateTime.now().minusHours(1);
        String mockUrl = "https://velog.io/@solchan";
        Account account = Account.builder().profile("").updatedAt(minusHours).build();
        account.updateProfile(mockUrl);
        assertAll(
                () -> assertEquals(mockUrl, account.getProfile()),
                () -> assertNotEquals(minusHours, account.getUpdatedAt())
        );
    }

    @Test
    @DisplayName("changeIsQuoteOfStatus 테스트")
    void changeIsQuoteOfStatusTest() {
        Account account = Account.builder().isQuote(false).build();
        account.changeIsQuoteOfStatus();
        assertTrue(account.isQuote());
    }
}
