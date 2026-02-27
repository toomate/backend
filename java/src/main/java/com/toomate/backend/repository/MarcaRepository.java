package com.toomate.backend.repository;

import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    List<Marca> findByNomeMarcaContainingIgnoreCase(String nome);
    Boolean existsByNomeMarca(String nome);

    @Query("select distinct m.fornecedor from Marca m where m.insumo.categoria.idCategoria = :idCategoria and m.fornecedor is not null")
    List<Fornecedor> findDistinctFornecedoresByCategoriaId(@Param("idCategoria") Integer idCategoria);
}
