package com.toomate.backend.categoria.domain.exception;

public class CategoriaJaExisteException extends RuntimeException {
    public CategoriaJaExisteException(String message) {
        super(message);
    }
}
