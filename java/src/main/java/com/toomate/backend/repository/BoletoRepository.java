package com.toomate.backend.repository;

import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoletoRepository extends JpaRepository <Boleto, Integer> {

    List<Boleto> findByCategoriaContainingIgnoreCase(String categoria);

    List<Boleto> findByIdFornecedor(Integer idFornecedor);

    Boolean existsByCategoria(String categoria);

    Cliente findByNome(String nome);

}
