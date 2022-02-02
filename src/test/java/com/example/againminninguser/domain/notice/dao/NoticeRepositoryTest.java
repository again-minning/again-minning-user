package com.example.againminninguser.domain.notice.dao;

import com.example.againminninguser.domain.notice.domain.Notice;
import com.example.againminninguser.domain.notice.domain.NoticeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Notice Repository 테스트")
public class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @BeforeAll
    void setNoticeData() {
        List<Notice> noticeList = new ArrayList<>();
        for(int i = 1; i <= 6; i++) {
            Notice notice = Notice.builder()
                    .id((long) i)
                    .title(i + "번 공지")
                    .content(i + "번 공지내용")
                    .createdAt(LocalDateTime.now().minusDays(1))
                    .updatedAt(LocalDateTime.now()).build();
            noticeList.add(notice);
        }
        noticeRepository.saveAll(noticeList);
    }

    @Test
    @DisplayName("findTop5ByIdGreaterThanOrderById 테스트 - Limit 테스트")
    void getNoticeList() {
        List<Notice> noticeList = noticeRepository.findTop5ByIdGreaterThanOrderById((long) 2);
        Assertions.assertEquals(4, noticeList.size());
    }
}
