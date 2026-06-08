package com.toomate.backend.repository;

import com.toomate.backend.model.Boleto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoletoRepository extends JpaRepository <Boleto, Integer> {

    Page<Boleto> findAll(Pageable pageable);

    List<Boleto> findByCategoriaContainingIgnoreCase(String categoria);

    List<Boleto> findByIdFornecedor(Integer idFornecedor);

    Boolean existsByCategoria(String categoria);

    @Query("select b.categoria from Boleto b group by b.categoria")
    List<String> listarCategorias();

    @Query(value = "SELECT * FROM boleto WHERE DATEDIFF(dataVencimento, CURDATE()) = :dias AND pago = 0", nativeQuery = true)
    List<Boleto> buscarBoletosPorDias(@Param("dias") Integer dias);
}

