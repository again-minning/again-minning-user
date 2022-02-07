package com.example.againminninguser.global.common.redis;

import com.example.againminninguser.domain.quote.domain.Quote;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("RedisTemplate 테스트")
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @AfterAll
    void deleteAll() {
        try {
            Set<String> keys = redisTemplate.keys("*");
            redisTemplate.delete(Objects.requireNonNull(keys));
        } catch (NullPointerException e) {
            System.out.println("Redis가 비어있습니다.");
        }
    }

    @Test
    @DisplayName("Set 테스트")
    void createTest() {
        //given
        Quote quote = Quote.builder().id(1L).content("꿀 잠을 자요").author("솔찬").build();
        final String AUTHOR_KEY = "Quote-Author";
        final String CONTENT_KEY = "Quote-Content";

        // when
        redisTemplate.opsForValue().set(AUTHOR_KEY, quote.getAuthor(), Duration.ofMillis(5000L));
        redisTemplate.opsForValue().set(CONTENT_KEY, quote.getContent());
        String author = redisTemplate.opsForValue().get(AUTHOR_KEY);
        String content = redisTemplate.opsForValue().get(CONTENT_KEY);

        // then
        assertThat(author).isEqualTo("솔찬");
        assertThat(content).isEqualTo("꿀 잠을 자요");
    }

    @Test
    @DisplayName("TTL 테스트")
    void expiredTest() throws InterruptedException {
        // given
        final String ACCESS_TOKEN = "eydasdasd...";

        // when
        redisTemplate.opsForValue().set(ACCESS_TOKEN, "BlackList", Duration.ofMillis(500L));
        Thread.sleep(1000L);
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);

        // then
        assertThat(accessToken).isNull();
    }
}
