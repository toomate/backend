package com.toomate.backend.service;

import com.toomate.backend.config.GerenciadorTokenJwt;
import lombok.extern.slf4j.Slf4j;
import com.toomate.backend.dto.usuario.*;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Marca;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    public  final AutenticacaoService autenticacaoService;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt, AuthenticationManager authenticationManager, AutenticacaoService autenticacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
        this.authenticationManager = authenticationManager;
        this.autenticacaoService = autenticacaoService;
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

        if (usuarioRepository.existsByApelidoIgnoreCase(request.getApelido())) {
            throw new RecursoExisteException("Já existe um usuário cadastrado com este apelido!");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());
        request.setSenha(senhaCriptografada);

        String usuarioLogado = getUsuarioLogado();

        Usuario usuario = UsuarioMapper.of(request);
        usuarioRepository.save(usuario);
        log.info("ADM {} criou usuário {}", usuarioLogado, usuario.getApelido());
        return UsuarioMapper.toResponse(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id));
        }

        String usuarioLogado = getUsuarioLogado();
        log.info("ADM {} deletou usuário dono do ID: {}", usuarioLogado, id);
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDto atualizar(Integer id, Usuario usuario) {
        String usuarioLogado = getUsuarioLogado();
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuario com o id %d", id));
        }
        usuario.setId(id);
        usuarioRepository.save(usuario);
        log.info("Usuário {} atualizou usuário {}", usuarioLogado, usuario.getApelido());
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto atualizarAdministrador(Integer id, AtualizarAdministradorDto adm){
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um usuário com o id %d", id)));

        String usuarioLogado = getUsuarioLogado();

        usuario.setAdministrador(adm.getadministrador());
        usuarioRepository.save(usuario);
        log.info("ADM {} tornou usuário {} um administrador do sistema", usuarioLogado, usuario.getApelido());
        return UsuarioMapper.toResponse(usuario);
    }

    public Usuario usuarioPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id)));
    }

    public UsuarioTokenDto autenticar(Usuario usuario){
        try {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getApelido(), usuario.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByApelido(usuario.getApelido());

        if (usuarioAutenticado.isEmpty()){
            throw new EntidadeNaoEncontradaException("Usuario não encontrado");
        }

        SecurityContextHolder.getContext().setAuthentication((authentication));

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        log.info("Usuário {} autenticado com sucesso", usuarioAutenticado.get().getApelido());
        return UsuarioMapper.of(usuarioAutenticado.get(), token);
        } catch (BadCredentialsException e) {
            log.warn("Falha na autenticação do usuário: {}", usuario.getApelido());
            throw e;
        }
    }

    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
