package com.example.againminninguser.global.error;

import com.example.againminninguser.global.common.content.AccountContent;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final HttpStatus status;
    public UserNotFoundException(){
        super(AccountContent.USER_NOT_FOUND);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public UserNotFoundException(String msg){
        super(msg);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
