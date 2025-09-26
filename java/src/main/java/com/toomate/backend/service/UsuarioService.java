package com.toomate.backend.service;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id)));
    }

    public Usuario cadastrar(Usuario usuario) {
        if (usuario == null) {
            throw new EntradaInvalidaException("O usuário não pode ser nulo!");
        }

        if (usuarioRepository.existsByNome(usuario.getNome())) {
            throw new RecursoExisteException("Já existe um usuário cadastrado com este nome");
        }

        return usuarioRepository.save(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id));
        }
        usuarioRepository.deleteById(id);
    }

    public Usuario atualizar(Integer id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuario com o id %d", id));
        }
        return usuarioRepository.save(usuario);
    }


}
