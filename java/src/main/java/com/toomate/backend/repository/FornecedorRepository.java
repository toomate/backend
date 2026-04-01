package com.toomate.backend.repository;

import com.toomate.backend.model.Fornecedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Integer> {
    List<Fornecedor> findByRazaoSocialContainingIgnoreCase(String razaoSocial);

    Page<Fornecedor> findAllByRazaoSocialContainingIgnoreCase(Pageable pageRequest, String razaoSocial);

    boolean existsByRazaoSocialIgnoreCase(String razaoSocial);
}
