package com.example.againminninguser.domain.quote.service;

import com.example.againminninguser.domain.quote.domain.Quote;
import com.example.againminninguser.domain.quote.domain.QuoteRepository;
import com.example.againminninguser.domain.quote.domain.dto.QuoteDto;
import com.example.againminninguser.global.common.content.QuoteContent;
import com.example.againminninguser.global.error.ServerErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final Random random;

    public QuoteDto getQuoteOfToday() {
        String author = redisTemplate.opsForValue().get("Quote-author");
        String content = redisTemplate.opsForValue().get("Quote-content");
        if(Objects.isNull(author) || Objects.isNull(content)) {
            throw new ServerErrorException(QuoteContent.QUOTE_IS_EMPTY);
        }
        return QuoteDto.builder().author(author).content(content).build();
    }

    public void updateQuoteOfToday() {
        long randomQuoteId = getRandomQuoteId();
        Quote quote = quoteRepository.findById(randomQuoteId).get();
        redisTemplate.delete("Quote-author");
        redisTemplate.delete("Quote-content");
        redisTemplate.opsForValue().set("Quote-author", quote.getAuthor());
        redisTemplate.opsForValue().set("Quote-content", quote.getContent());
    }

    private long getRandomQuoteId() {
        List<Long> idList = quoteRepository.findIdList();
        int randomIndex = random.nextInt(idList.size());
        return idList.get(randomIndex);
    }
}
