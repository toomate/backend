package com.toomate.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

<<<<<<< Updated upstream
@ResponseStatus(HttpStatus.CONFLICT)
=======
@ResponseStatus(HttpStatus.CONFLICT  )
>>>>>>> Stashed changes
public class RecursoExisteException extends RuntimeException {
    public RecursoExisteException(String message) {
        super(message);
    }
}
