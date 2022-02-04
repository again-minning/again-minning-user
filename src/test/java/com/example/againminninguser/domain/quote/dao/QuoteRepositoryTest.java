package com.example.againminninguser.domain.quote.dao;

import com.example.againminninguser.domain.quote.domain.Quote;
import com.example.againminninguser.domain.quote.domain.QuoteRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Quote Repository 테스트")
public class QuoteRepositoryTest {

    @Autowired
    private QuoteRepository quoteRepository;

    @Test
    void findIdListTest() {
        Quote build = Quote.builder().author("솔찬").content("놀자").build();
        Quote build1 = Quote.builder().author("솔찬2").content("자자").build();
        Quote build2 = Quote.builder().author("솔찬3").content("쉬자").build();
        quoteRepository.saveAll(List.of(build, build1, build2));

        List<Long> idList = quoteRepository.findIdList();

        assertEquals(3, idList.size());
    }

}
