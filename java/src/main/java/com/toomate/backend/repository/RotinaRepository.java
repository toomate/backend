package com.toomate.backend.repository;

import com.toomate.backend.model.Rotina;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RotinaRepository extends JpaRepository<Rotina, Integer> {

    List<Rotina> findByTituloContainsIgnoreCase(String titulo);
}
