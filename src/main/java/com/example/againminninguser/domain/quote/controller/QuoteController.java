package com.example.againminninguser.domain.quote.controller;

import com.example.againminninguser.domain.account.domain.Account;
import com.example.againminninguser.domain.quote.domain.dto.QuoteDto;
import com.example.againminninguser.domain.quote.service.QuoteService;
import com.example.againminninguser.global.common.content.QuoteContent;
import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import com.example.againminninguser.global.util.AuthAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/quote")
public class QuoteController {

    private final QuoteService quoteService;

    @PatchMapping("/perform")
    public CustomResponseEntity<Message> performQuoteOfToday(
            @AuthAccount Account account, @RequestBody QuoteDto quoteDto) {
        quoteService.performQuoteOfToday(account, quoteDto);
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, QuoteContent.QUOTE_PERFORM_OK)
        );
    }

    @GetMapping("")
    public CustomResponseEntity<QuoteDto> getQuoteOfToday() {
        return new CustomResponseEntity<>(
                Message.of(HttpStatus.OK, QuoteContent.QUOTE_OF_TODAY_OK),
                quoteService.getQuoteOfToday()
        );
    }

    @GetMapping("/update")
    public CustomResponseEntity<Message> updateQuoteOfToday() {
        boolean result = quoteService.updateQuoteOfToday();
        return result
                ? new CustomResponseEntity<>(
                    Message.of(HttpStatus.OK, QuoteContent.QUOTE_UPDATE_OK)
                )
                : new CustomResponseEntity<>(
                    Message.of(HttpStatus.INTERNAL_SERVER_ERROR, QuoteContent.QUOTE_UPDATE_ERROR)
                );
    }
}
