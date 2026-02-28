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

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            GerenciadorTokenJwt gerenciadorTokenJwt
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    }

    public List<UsuarioResponseDto> listar() {
        return UsuarioMapper.toResponse(usuarioRepository.findAll());
    }

    public UsuarioResponseDto buscarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um usuario com o id %d", id))
        );
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto buscarPorNome(String nome) {
        Usuario usuario = usuarioRepository.findByNomeIgnoreCase(nome).orElseThrow(
                () -> new EntidadeNaoEncontradaException("Nao foi encontrado um usuario com este nome!")
        );
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto cadastrar(UsuarioRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("O usuario nao pode ser nulo.");
        }

        if (usuarioRepository.existsByNomeIgnoreCase(request.getNome())) {
            throw new RecursoExisteException("Ja existe um usuario cadastrado com este nome");
        }

        request.setSenha(encodeIfNeeded(request.getSenha()));

        Usuario usuario = UsuarioMapper.of(request);
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public void deletar(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado um usuario com o id %d", id));
        }
        usuarioRepository.deleteById(id);
    }

    public UsuarioResponseDto atualizar(Integer id, Usuario request) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um usuario com o id %d", id))
        );

        if (request.getNome() != null && !request.getNome().isBlank()) {
            usuario.setNome(request.getNome().trim());
        }

        if (request.getAdministrador() != null) {
            usuario.setAdministrador(request.getAdministrador());
        }

        if (request.getSenha() != null && !request.getSenha().isBlank()) {
            usuario.setSenha(encodeIfNeeded(request.getSenha()));
        }

        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    public UsuarioResponseDto atualizarAdministrador(Integer id, AtualizarAdministradorDto adm) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um usuario com o id %d", id))
        );

        usuario.setAdministrador(adm.getadministrador());
        usuarioRepository.save(usuario);

        return UsuarioMapper.toResponse(usuario);
    }

    public Usuario usuarioPorId(Integer id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um usuario com o id %d", id))
        );
    }

    public UsuarioTokenDto autenticar(Usuario usuario) {
        final UsernamePasswordAuthenticationToken credentials =
                new UsernamePasswordAuthenticationToken(usuario.getNome(), usuario.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Optional<Usuario> usuarioAutenticado = usuarioRepository.findByNome(usuario.getNome());

        if (usuarioAutenticado.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuario nao encontrado");
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado.get(), token);
    }

    private String encodeIfNeeded(String senha) {
        if (senha == null || senha.isBlank()) {
            throw new EntradaInvalidaException("A senha do usuario nao pode ser vazia.");
        }

        if (isBcryptHash(senha)) {
            return senha;
        }

        return passwordEncoder.encode(senha);
    }

    private boolean isBcryptHash(String senha) {
        return senha != null && senha.matches("^\\$2[aby]?\\$\\d\\d\\$[./A-Za-z0-9]{53}$");
    }
}
