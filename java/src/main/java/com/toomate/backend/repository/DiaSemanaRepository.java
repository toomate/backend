package com.toomate.backend.repository;

import com.toomate.backend.model.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Integer> {
    @Query
    Optional<DiaSemana> findByNomeDia(String nomeDia);
}
