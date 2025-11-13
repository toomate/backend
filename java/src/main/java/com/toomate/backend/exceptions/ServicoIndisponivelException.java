package com.toomate.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServicoIndisponivelException extends RuntimeException {
    public ServicoIndisponivelException(String message) {
        super(message);
    }
}
