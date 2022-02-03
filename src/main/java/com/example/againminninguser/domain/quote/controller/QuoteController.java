package com.example.againminninguser.domain.quote.controller;

import com.example.againminninguser.domain.quote.domain.dto.QuoteDto;
import com.example.againminninguser.domain.quote.service.QuoteService;
import com.example.againminninguser.global.common.content.QuoteContent;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quote")
public class QuoteController {

    private final QuoteService quoteService;

    @GetMapping("")
    public CustomResponseEntity<QuoteDto> getQuoteOfToday() {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, QuoteContent.QUOTE_OF_TODAY_OK),
                quoteService.getQuoteOfToday()
        );
    }

    @GetMapping("/update")
    public void updateQuoteOfToday() {
        quoteService.updateQuoteOfToday();
    }
}
