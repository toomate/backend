package com.toomate.backend.repository;

import com.toomate.backend.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
    @Query
    Optional<Arquivo> findByChave(String chave);
}
