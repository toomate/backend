package com.toomate.backend.categoria.domain.exception;

public class CategoriaNaoEncontradaException extends RuntimeException {
    public CategoriaNaoEncontradaException(String message) {
        super(message);
    }
}
