package com.Social.application.DG2.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedException extends RuntimeException{
    private static final long serialVersionUID = -4893320765855582206L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
