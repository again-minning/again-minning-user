package com.example.againminninguser.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class Message {
    private HttpStatus status;
    private String msg;

    public static Message of(HttpStatus status, String msg) {
        return new Message(status, msg);
    }
}