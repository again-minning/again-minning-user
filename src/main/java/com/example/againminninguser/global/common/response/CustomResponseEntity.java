package com.example.againminninguser.global.common.response;

import lombok.Data;

@Data
public class CustomResponseEntity<T> {
    private Message message;
    private Object data;

    public CustomResponseEntity(Message message, T data) {
        this.message = message;
        this.data = data;
    }

    public CustomResponseEntity(Message message) {
        this.message = message;
    }
}
