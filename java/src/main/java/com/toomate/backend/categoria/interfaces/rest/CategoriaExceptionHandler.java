package com.toomate.backend.categoria.interfaces.rest;

import com.toomate.backend.categoria.domain.exception.CategoriaInvalidaException;
import com.toomate.backend.categoria.domain.exception.CategoriaJaExisteException;
import com.toomate.backend.categoria.domain.exception.CategoriaNaoEncontradaException;
import com.toomate.backend.controller.CategoriaController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = CategoriaController.class)
public class CategoriaExceptionHandler {

    @ExceptionHandler(CategoriaNaoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(CategoriaNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("mensagem", ex.getMessage()));
    }

    @ExceptionHandler(CategoriaJaExisteException.class)
    public ResponseEntity<Map<String, String>> handleConflict(CategoriaJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("mensagem", ex.getMessage()));
    }

    @ExceptionHandler(CategoriaInvalidaException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(CategoriaInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("mensagem", ex.getMessage()));
    }
}
