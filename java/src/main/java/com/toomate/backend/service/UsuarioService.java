package com.toomate.backend.service;

import com.toomate.backend.config.GerenciadorTokenJwt;
import com.toomate.backend.dto.usuario.AtualizarAdministradorDto;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.dto.usuario.UsuarioTokenDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, GerenciadorTokenJwt gerenciadorTokenJwt) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    public List<UsuarioResponseDto> listar() {
        return UsuarioMapper.toResponse(usuarioRepository.findAll());
    }

    public UsuarioResponseDto buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id)));

        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto buscarPorNome(String nome){
        Usuario usuario = usuarioRepository.findByNome(nome).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrado um usuário com este nome!"));

        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto cadastrar(UsuarioRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("O usuário não pode ser nulo!");
        }

        if (usuarioRepository.existsByNome(request.getNome())) {
            throw new RecursoExisteException("Já existe um usuário cadastrado com este nome");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());
        request.setSenha(senhaCriptografada);

        Usuario usuario = UsuarioMapper.of(request);
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id));
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDto atualizar(Integer id, Usuario usuario) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuario com o id %d", id));
        }
        usuario.setId(id);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto atualizarAdministrador(Integer id, AtualizarAdministradorDto adm){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id)));

        usuario.setAdministrador(adm.getadministrador());
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }


}
