package com.toomate.backend.repository;

import com.toomate.backend.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByNomeContainingIgnoreCase(String nome);
    Boolean existsByNome(String nome);
    Void deleteByNome(String nome);
}
