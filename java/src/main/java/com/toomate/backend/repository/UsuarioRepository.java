package com.toomate.backend.repository;

import com.toomate.backend.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Boolean existsByNome(String nome);
    Boolean existsByNomeIgnoreCase(String nome);

    Boolean existsByApelidoIgnoreCase(String apelido);

    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findByNomeIgnoreCase(String nome);

    Optional<Usuario> findByApelidoIgnoreCase(String apelido);
}
