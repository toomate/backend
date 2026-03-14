package com.toomate.backend.repository;

import com.toomate.backend.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepository  extends JpaRepository <Cliente, Integer> {

    List<Cliente> findByNomeContainingIgnoreCase(String nome);

    Boolean existsByNome(String nome);

    Cliente findByNome(String nome);

    @Query("select count(c) from Cliente c join c.dividas d where d.pago = false")
    Integer findAllByNotPago();
}
