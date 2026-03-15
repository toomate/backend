package com.toomate.backend.repository;

import com.toomate.backend.model.RotinaInsumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RotinaInsumoRepository extends JpaRepository<RotinaInsumo, Integer> {
    Optional<RotinaInsumo> findByRotinaId(Integer rotinaId);
    @Modifying
    @Query("DELETE FROM RotinaInsumo r WHERE r.rotina.id = :id")
    void deleteByRotinaId(Integer id);
}
