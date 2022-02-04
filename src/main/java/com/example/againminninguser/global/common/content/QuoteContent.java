package com.example.againminninguser.global.common.content;

import lombok.Data;

@Data
public class QuoteContent {

    private QuoteContent() {}

    public static final String QUOTE_OF_TODAY_OK = "오늘의 명언 조회를 성공하였습니다.";
    public static final String QUOTE_IS_EMPTY = "오늘의 명언이 비어있습니다.";
    public static final String QUOTE_UPDATE_OK = "오늘의 명언 업데이트를 성공하였습니다.";
    public static final String QUOTE_PERFORM_OK = "오늘의 명언 수행을 성공하였습니다.";

    public static final String QUOTE_UPDATE_ERROR = "오늘의 명언 업데이트를 실패하였습니다.";
    public static final String QUOTE_IS_NOT_EQUAL = "오늘의 명언이 일치하지 않습니다.";
}
