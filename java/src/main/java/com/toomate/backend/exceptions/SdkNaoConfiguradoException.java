package com.toomate.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SdkNaoConfiguradoException extends RuntimeException {
    public SdkNaoConfiguradoException(String message) {
        super(message);
    }
}
