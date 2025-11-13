package com.toomate.backend.service;

import com.toomate.backend.dto.usuario.UsuarioDetalhesDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public AutenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByNome(username).orElseThrow(() -> new UsernameNotFoundException("Você precisa estar logado para acessar este recurso!"));

        return new UsuarioDetalhesDto(usuario);
    }
}
