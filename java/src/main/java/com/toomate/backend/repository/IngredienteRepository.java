package com.toomate.backend.repository;

import com.toomate.backend.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    List<Ingrediente> findByNomeContainingIgnoreCase(String nome);
    Optional<Ingrediente> findByNomeAndFkCategoria(String nome, Integer fkCategoria);
    Boolean existsByNome(String nome);
    Ingrediente findByNome(String nome);
}
