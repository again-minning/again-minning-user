package com.example.againminninguser.global.error;

import com.example.againminninguser.global.common.content.AccountContent;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RefreshTokenBadRequestException extends RuntimeException {
    private final HttpStatus status;
    public RefreshTokenBadRequestException(){
        super(AccountContent.EXPIRED_TOKEN);
        this.status = HttpStatus.BAD_REQUEST;
    }
}
