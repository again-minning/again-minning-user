package com.example.againminninguser.global.erorr;

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
}
