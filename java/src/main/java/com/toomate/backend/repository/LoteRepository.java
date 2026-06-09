package com.toomate.backend.repository;

import com.toomate.backend.dto.estoque_grupo.EstoqueGeral;
import com.toomate.backend.dto.estoque_grupo.EstoqueVencimento;
import com.toomate.backend.model.Lote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Integer> {
        
List<Lote> findAllByAtivoTrueAndQuantidadeAtualGreaterThanZero();


    //validar cache
    @Query("select  COALESCE(SUM(L.quantidadeAtual), 0) from Lote L JOIN L.marca M JOIN M.insumo I WHERE I.idInsumo = :idInsumo")
    Double getEstoqueInsumo(@Param("idInsumo") Integer idInsumo);

    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            i.qtdMinima,
            m.idMarca,
            m.nomeMarca,
            l.idLote,
            l.quantidadeAtual,
            l.quantidadeMedida,
            l.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            WHERE l.quantidadeAtual >= 0
            AND i.ativo = true
            ORDER BY l.quantidadeAtual ASC,
            l.dataValidade ASC,
            i.nome ASC
            """)
    List<EstoqueGeral> buscarEstoque();


    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            i.qtdMinima,
            m.idMarca,
            m.nomeMarca,
            l.idLote,
            l.quantidadeAtual,
            l.quantidadeMedida,
            l.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            WHERE l.quantidadeAtual >= 0
            AND i.ativo = true
            AND c.nome = :categoria
            ORDER BY l.quantidadeAtual ASC,
            l.dataValidade ASC,
            i.nome ASC
            """)
    List<EstoqueGeral> buscarEstoquePorCategoria(String categoria);

    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueGeral (
            c.idCategoria,
            c.nome,
            i.idInsumo,
            i.nome,
            i.qtdMinima,
            m.idMarca,
            m.nomeMarca,
            l.idLote,
            l.quantidadeAtual,
            l.quantidadeMedida,
            l.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            JOIN i.categoria c
            WHERE l.quantidadeAtual >= 0
            AND i.ativo = true
            AND i.nome LIKE %:insumo%
            ORDER BY l.quantidadeAtual ASC,
            l.dataValidade ASC,
            i.nome ASC
            """)
    List<EstoqueGeral> pesquisarEstoquePorInsumo(String insumo);

    @Query("""
            SELECT new com.toomate.backend.dto.estoque_grupo.EstoqueVencimento (
            l.idLote,
            i.nome,
            m.nomeMarca,
            l.quantidadeMedida,
            l.unidadeMedida,
            l.dataValidade
            )
            FROM Lote l
            JOIN l.marca m
            JOIN m.insumo i
            WHERE l.quantidadeAtual > 0
            AND i.ativo = true
            ORDER BY l.dataValidade""")
    List<EstoqueVencimento> buscarEstoqueVencimento();

    @Query("SELECT l FROM Lote l WHERE l.marca.insumo.idInsumo = :idInsumo ORDER BY l.dataValidade ASC")
    List<Lote> lotePorIdInsumo(Integer idInsumo);

    Page<Lote> findByDataEntradaBetween(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);

    @Query("""
            SELECT COALESCE(SUM(l.precoUnitario * COALESCE(l.quantidadeOriginal, l.quantidadeAtual)), 0)
            FROM Lote l
            WHERE l.dataEntrada BETWEEN :dataInicial AND :dataFinal
            """)
    Double somarValorPorPeriodo(@Param("dataInicial") LocalDate dataInicial,
                                @Param("dataFinal") LocalDate dataFinal);

    @Query("""
            SELECT COALESCE(SUM(l.precoUnitario * COALESCE(l.quantidadeOriginal, l.quantidadeAtual)), 0) FROM Lote l
            """)
    Double somarValorTotal();

    long countByDataEntradaBetween(LocalDate dataInicial, LocalDate dataFinal);

    @Query(value = "SELECT * FROM lote as l WHERE DATEDIFF(l.dataValidade, CURDATE()) = :dias AND l.ativo = 1 AND l.quantidadeAtual > 0;", nativeQuery = true)
    List<Lote> buscarLotePorDia(@Param("dias") Integer dias);
}
