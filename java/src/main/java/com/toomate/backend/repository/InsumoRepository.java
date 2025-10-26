package com.toomate.backend.repository;

import com.toomate.backend.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InsumoRepository extends JpaRepository<Insumo, Integer> {
    List<Insumo> findByNomeContainingIgnoreCase(String nome);
    Boolean existsByNome(String nome);
    Insumo findByNome(String nome);

}
