package com.example.againminninguser.global.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class Message {
    private HttpStatus status;
    private String msg;
}