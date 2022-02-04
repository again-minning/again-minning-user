package com.example.againminninguser.domain.quote.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteDto {
    private String author;
    private String content;
}
