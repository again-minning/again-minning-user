package com.example.againminninguser.global.common.content;

import lombok.Data;

@Data
public class QuoteContent {

    private QuoteContent() {}

    public static final String QUOTE_OF_TODAY_OK = "오늘의 명언 조회를 성공하였습니다.";
    public static final String QUOTE_IS_EMPTY = "오늘의 명언이 비어있습니다.";
}
