package com.toomate.backend.categoria.application.port.out;

import com.toomate.backend.categoria.domain.model.CategoriaDomain;

import java.util.List;
import java.util.Optional;

public interface CategoriaGateway {
    List<CategoriaDomain> findAll();

    List<CategoriaDomain> findByNomeContainingIgnoreCase(String nome);

    Optional<CategoriaDomain> findById(Integer id);

    CategoriaDomain save(CategoriaDomain categoria);

    boolean existsById(Integer id);

    boolean existsByNomeIgnoreCase(String nome);

    void deleteById(Integer id);
}
