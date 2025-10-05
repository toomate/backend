package com.toomate.backend.repository;

import com.toomate.backend.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    List<Marca> findByNomeMarcaContainingIgnoreCase(String nome);
    Boolean existsByNomeMarca(String nome);
}
