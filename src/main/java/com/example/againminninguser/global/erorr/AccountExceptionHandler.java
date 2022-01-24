package com.example.againminninguser.global.erorr;

import com.example.againminninguser.global.common.response.CustomResponseEntity;
import com.example.againminninguser.global.common.response.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseEntity<Message> handle(UserNotFoundException e){
        Message message = Message.builder().msg(e.getMessage()).status(e.getStatus()).build();
        return new CustomResponseEntity<>(message);
    }

    @ExceptionHandler(RefreshTokenBadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomResponseEntity<Message> handle(RefreshTokenBadRequestException e){
        Message message = Message.builder().msg(e.getMessage()).status(e.getStatus()).build();
        return new CustomResponseEntity<>(message);
    }
}