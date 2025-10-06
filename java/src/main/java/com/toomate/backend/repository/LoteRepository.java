package com.toomate.backend.repository;

import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
}
