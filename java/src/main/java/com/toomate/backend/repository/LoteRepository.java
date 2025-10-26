package com.toomate.backend.repository;

import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

    @Query("select  COALESCE(SUM(L.quantidadeMedida), 0) from Lote L JOIN L.marca M JOIN M.insumo I WHERE I.idInsumo = :idInsumo")
    Double getEstoqueInsumo(@Param("idInsumo") Integer idInsumo);

}
