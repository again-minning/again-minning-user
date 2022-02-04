package com.example.againminninguser.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServerErrorException extends RuntimeException {
    private final HttpStatus status;
    public ServerErrorException(String message){
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
