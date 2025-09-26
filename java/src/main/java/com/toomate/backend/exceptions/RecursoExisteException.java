package com.toomate.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RecursoExisteException extends RuntimeException {
    public RecursoExisteException(String message) {
        super(message);
    }
}
