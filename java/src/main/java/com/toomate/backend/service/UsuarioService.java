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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    public final AutenticacaoService autenticacaoService;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            GerenciadorTokenJwt gerenciadorTokenJwt,
            AuthenticationManager authenticationManager,
            AutenticacaoService autenticacaoService
    ) {
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
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um usuario com o id %d", id))
        );

        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto buscarPorNome(String nome) {
        Usuario usuario = usuarioRepository.findByNome(nome).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Nao foi encontrado um usuario com este nome!")
        );

        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto cadastrar(UsuarioRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("O usuario nao pode ser nulo!");
        }

        if (usuarioRepository.existsByApelidoIgnoreCase(request.getNome())) {
            throw new RecursoExisteException("Ja existe um usuario cadastrado com este apelido!");
        }

        String senhaCriptografada = passwordEncoder.encode(request.getSenha());
        request.setSenha(senhaCriptografada);

        Usuario usuario = UsuarioMapper.of(request);
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um usuario com o id %d", id));
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDto atualizar(Integer id, Usuario usuario) {
        Usuario usuarioAtual = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um usuario com o id %d", id))
        );

        if (usuario.getSenha() == null || usuario.getSenha().isBlank()) {
            usuario.setSenha(usuarioAtual.getSenha());
        } else if (!isBcryptHash(usuario.getSenha())) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        usuario.setId(id);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto atualizarAdministrador(Integer id, AtualizarAdministradorDto adm) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(String.format("Nao foi encontrado um usuario com o id %d", id))
        );

        usuario.setAdministrador(adm.getadministrador());
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public Usuario usuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(String.format("Nao foi encontrado nenhum usuario com o id %d", id))
        );
    }

    public UsuarioTokenDto autenticar(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                usuario.getApelido(),
                usuario.getSenha()
        );

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByApelido(usuario.getApelido());

        if (usuarioAutenticado.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuario nao encontrado");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado.get(), token);
    }

    private boolean isBcryptHash(String senha) {
        return senha != null && senha.matches("^\\$2[aby]?\\$\\d\\d\\$[./A-Za-z0-9]{53}$");
    }
}
