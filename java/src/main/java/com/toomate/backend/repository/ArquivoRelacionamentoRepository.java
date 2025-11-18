package com.toomate.backend.repository;

import com.toomate.backend.model.Arquivo;
import com.toomate.backend.model.ArquivoRelacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArquivoRelacionamentoRepository extends JpaRepository<ArquivoRelacionamento, Integer> {
    @Query
    Optional<ArquivoRelacionamento> findByArquivo(Arquivo arquivo);
}
