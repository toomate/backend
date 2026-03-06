package com.toomate.backend.service;

import com.toomate.backend.config.GerenciadorTokenJwt;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private GerenciadorTokenJwt gerenciadorTokenJwt;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void cadastrarDeveHasharSenhaESalvar() {
        UsuarioRequestDto request = new UsuarioRequestDto("Lucas", "lucas.dev", "lucas123", true);

        when(usuarioRepository.existsByApelidoIgnoreCase("lucas.dev")).thenReturn(false);
        when(passwordEncoder.encode("lucas123")).thenReturn("$2a$10$hashGeradoTeste12345678901234567890123456789012345678901");

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1);
        usuarioSalvo.setNome("Lucas");
        usuarioSalvo.setApelido("lucas.dev");
        usuarioSalvo.setSenha("$2a$10$hashGeradoTeste12345678901234567890123456789012345678901");
        usuarioSalvo.setAdministrador(true);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponseDto response = usuarioService.cadastrar(request);

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());

        assertEquals("Lucas", response.getNome());
        assertEquals("lucas.dev", response.getApelido());
        assertEquals(true, response.getAdministrador());
        assertEquals("$2a$10$hashGeradoTeste12345678901234567890123456789012345678901", captor.getValue().getSenha());
        verify(passwordEncoder).encode("lucas123");
    }

    @Test
    void cadastrarDeveBloquearDuplicidadeDeApelidoIgnorandoCase() {
        UsuarioRequestDto request = new UsuarioRequestDto("Lucas", "LUCAS.DEV", "lucas123", false);
        when(usuarioRepository.existsByApelidoIgnoreCase("LUCAS.DEV")).thenReturn(true);

        assertThrows(RecursoExisteException.class, () -> usuarioService.cadastrar(request));

        verify(usuarioRepository, never()).save(any(Usuario.class));
        verify(passwordEncoder, never()).encode(any(String.class));
    }

    @Test
    void buscarPorNomeDeveUsarIgnoreCase() {
        Usuario usuario = new Usuario();
        usuario.setId(7);
        usuario.setNome("lucas");
        usuario.setApelido("lucas.dev");
        usuario.setSenha("hash");
        usuario.setAdministrador(false);

        when(usuarioRepository.findByNomeIgnoreCase("LUCAS")).thenReturn(Optional.of(usuario));

        UsuarioResponseDto response = usuarioService.buscarPorNome("LUCAS");

        assertEquals("lucas", response.getNome());
        assertEquals("lucas.dev", response.getApelido());
        verify(usuarioRepository).findByNomeIgnoreCase("LUCAS");
    }

    @Test
    void buscarPorNomeDeveLancarQuandoNaoEncontrar() {
        when(usuarioRepository.findByNomeIgnoreCase("inexistente")).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> usuarioService.buscarPorNome("inexistente"));
    }
}
