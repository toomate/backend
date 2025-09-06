package com.toomate.backend.repository;

import com.toomate.backend.model.MarcaIngrediente;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaIngredienteRepository extends JpaRepository<MarcaIngrediente, Integer> {
    List<MarcaIngrediente> findByDescricaoContainingIgnoreCase(String descricao);
    Optional<MarcaIngrediente> findByDescricaoAndValorMedidaAndFkIngredienteAndFkFornecedor(String descricao, Double valorMedida, Integer fkIngrediente, Integer fkFornecedor);
    List<MarcaIngrediente> findByValorMedida(Double valorMedida);
    List<MarcaIngrediente> findByDescricaoContainingIgnoreCaseAndValorMedida(String descricao, Double valorMedida);


}
