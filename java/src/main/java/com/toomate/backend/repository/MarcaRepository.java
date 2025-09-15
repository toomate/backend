package com.toomate.backend.repository;

import com.toomate.backend.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    List<Marca> findByDescricaoContainingIgnoreCase(String descricao);
    Optional<Marca> findByDescricaoAndValorMedidaAndFkInsumoAndFkFornecedor(String descricao, Double valorMedida, Integer fkInsumo, Integer fkFornecedor);
    List<Marca> findByValorMedida(Double valorMedida);
    List<Marca> findByDescricaoContainingIgnoreCaseAndValorMedida(String descricao, Double valorMedida);


}
