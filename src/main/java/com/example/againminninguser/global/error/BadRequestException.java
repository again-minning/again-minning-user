package com.example.againminninguser.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException {
    private final HttpStatus status;
    public BadRequestException(String message){
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
