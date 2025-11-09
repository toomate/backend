package com.toomate.backend.repository;

import com.toomate.backend.model.Arquivo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArquivoRepository extends JpaRepository<Arquivo, Integer> {
}
