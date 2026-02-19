package com.toomate.backend.repository;

import com.toomate.backend.dto.estoque_grupo.EstoqueGeral;
import com.toomate.backend.model.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {

    @Query("select  COALESCE(SUM(L.quantidadeMedida), 0) from Lote L JOIN L.marca M JOIN M.insumo I WHERE I.idInsumo = :idInsumo")
    Double getEstoqueInsumo(@Param("idInsumo") Integer idInsumo);

    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            m.nomeMarca,
            l.quantidadeMedida,
            i.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            """)
    List<EstoqueGeral> buscarEstoque();


    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            m.nomeMarca,
            l.quantidadeMedida,
            i.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            WHERE c.nome = :categoria
            """)
    List<EstoqueGeral> buscarEstoquePorCategoria(String categoria);

    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            m.nomeMarca,
            l.quantidadeMedida,
            i.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            WHERE i.nome LIKE %:insumo%
            """)
    List<EstoqueGeral> pesquisarEstoquePorInsumo(String insumo);

}
