package com.Social.application.DG2.util.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends RuntimeException  {
    public ForbiddenException(String message) {
        super(message);

    }
}
