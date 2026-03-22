package com.toomate.backend.repository;

import com.toomate.backend.model.Divida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DividaRepository  extends JpaRepository<Divida, Integer> {


    @Query("select d.pedido from Divida d group by d.pedido")
    List<String> listarPedidos();
}
