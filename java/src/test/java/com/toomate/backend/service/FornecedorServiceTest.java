package com.toomate.backend.service;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.repository.FornecedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    private Fornecedor exemploFornecedor;
    private FornecedorRequestDto requestValido;

    @BeforeEach
    void setup() {
        exemploFornecedor = new Fornecedor();
        exemploFornecedor.setId(1);
        exemploFornecedor.setLink("https://wa.me/5511987654321");
        exemploFornecedor.setRazaoSocial("Atacado Sao Paulo");
        exemploFornecedor.setTelefone("11987654321");

        requestValido = new FornecedorRequestDto(
                "Atacado Sao Paulo",
                "11987654321"
        );
    }

    @Test
    void listarTeste() {
        when(fornecedorRepository.findAll()).thenReturn(List.of(exemploFornecedor));

        List<Fornecedor> resultado = fornecedorService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(exemploFornecedor, resultado.get(0));
        verify(fornecedorRepository, times(1)).findAll();
    }

    @Test
    void retornarPeloIdSucessoTeste() {
        when(fornecedorRepository.findById(1)).thenReturn(Optional.of(exemploFornecedor));

        Fornecedor retornado = fornecedorService.retornarPeloId(1);

        assertNotNull(retornado);
        assertEquals(exemploFornecedor.getId(), retornado.getId());
        verify(fornecedorRepository, times(1)).findById(1);
    }

    @Test
    void retornarPeloIdNaoEncontradoTeste() {
        when(fornecedorRepository.findById(2)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.retornarPeloId(2));

        assertTrue(ex.getMessage().contains("Nao foi encontrado um fornecedor com o id 2"));
        verify(fornecedorRepository, times(1)).findById(2);
    }

    @Test
    void filtrarPorRazaoSocialTeste() {
        when(fornecedorRepository.findByRazaoSocialContainingIgnoreCase("Atacado"))
                .thenReturn(List.of(exemploFornecedor));

        List<Fornecedor> resultado = fornecedorService.filtrar("Atacado");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(exemploFornecedor, resultado.get(0));
        verify(fornecedorRepository, times(1)).findByRazaoSocialContainingIgnoreCase("Atacado");
    }

    @Test
    void filtrarEmBrancoRetornaTodosTeste() {
        when(fornecedorRepository.findAll()).thenReturn(List.of(exemploFornecedor));

        List<Fornecedor> resultado = fornecedorService.filtrar("   ");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(fornecedorRepository, times(1)).findAll();
        verify(fornecedorRepository, never()).findByRazaoSocialContainingIgnoreCase(any());
    }

    @Test
    void cadastrarSucessoTeste() {
        Fornecedor salvo = new Fornecedor();
        salvo.setId(5);
        salvo.setLink("https://wa.me/5511987654321");
        salvo.setRazaoSocial(requestValido.getRazaoSocial());
        salvo.setTelefone("5511987654321");

        when(fornecedorRepository.existsByRazaoSocialIgnoreCase("Atacado Sao Paulo")).thenReturn(false);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(salvo);

        Fornecedor resultado = fornecedorService.cadastrar(requestValido);

        assertNotNull(resultado);
        assertEquals(5, resultado.getId());
        assertEquals(requestValido.getRazaoSocial(), resultado.getRazaoSocial());
        verify(fornecedorRepository, times(1)).existsByRazaoSocialIgnoreCase("Atacado Sao Paulo");
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void cadastrarEntradaNulaTeste() {
        EntradaInvalidaException ex = assertThrows(EntradaInvalidaException.class,
                () -> fornecedorService.cadastrar(null));

        assertTrue(ex.getMessage().contains("O fornecedor nao pode ser nulo."));
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void cadastrarRazaoSocialDuplicadaTeste() {
        when(fornecedorRepository.existsByRazaoSocialIgnoreCase("Atacado Sao Paulo")).thenReturn(true);

        RecursoExisteException ex = assertThrows(RecursoExisteException.class,
                () -> fornecedorService.cadastrar(requestValido));

        assertTrue(ex.getMessage().contains("Ja existe um fornecedor com essa razao social."));
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void atualizarSucessoTeste() {
        FornecedorRequestDto atualizacao = new FornecedorRequestDto(
                "Novo Nome",
                "11999999999"
        );
        Fornecedor existente = new Fornecedor();
        existente.setId(1);
        existente.setRazaoSocial("Nome Antigo");
        existente.setLink("https://old.link");
        existente.setTelefone("11111111111");

        when(fornecedorRepository.findById(1)).thenReturn(Optional.of(existente));
        when(fornecedorRepository.existsByRazaoSocialIgnoreCase("Novo Nome")).thenReturn(false);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Fornecedor resultado = fornecedorService.atualizar(1, atualizacao);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Novo Nome", resultado.getRazaoSocial());
        assertEquals("https://wa.me/5511999999999", resultado.getLink());
        assertEquals("5511999999999", resultado.getTelefone());
        verify(fornecedorRepository, times(1)).findById(1);
        verify(fornecedorRepository, times(1)).existsByRazaoSocialIgnoreCase("Novo Nome");
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void atualizarNaoEncontradoTeste() {
        when(fornecedorRepository.findById(2)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.atualizar(2, requestValido));

        assertTrue(ex.getMessage().contains("Nao foi encontrado um fornecedor com o id 2"));
        verify(fornecedorRepository, times(1)).findById(2);
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void atualizarRazaoSocialDuplicadaTeste() {
        FornecedorRequestDto atualizacao = new FornecedorRequestDto(
                "Fornecedor Duplicado",
                "11999999999"
        );
        Fornecedor existente = new Fornecedor();
        existente.setId(1);
        existente.setRazaoSocial("Nome Antigo");

        when(fornecedorRepository.findById(1)).thenReturn(Optional.of(existente));
        when(fornecedorRepository.existsByRazaoSocialIgnoreCase("Fornecedor Duplicado")).thenReturn(true);

        RecursoExisteException ex = assertThrows(RecursoExisteException.class,
                () -> fornecedorService.atualizar(1, atualizacao));

        assertTrue(ex.getMessage().contains("Ja existe um fornecedor com essa razao social."));
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void deletarSucessoTeste() {
        when(fornecedorRepository.existsById(1)).thenReturn(true);

        fornecedorService.deletar(1);

        verify(fornecedorRepository, times(1)).existsById(1);
        verify(fornecedorRepository, times(1)).deleteById(1);
    }

    @Test
    void deletarNaoEncontradoTeste() {
        when(fornecedorRepository.existsById(2)).thenReturn(false);

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.deletar(2));

        assertTrue(ex.getMessage().contains("Nao foi encontrado um fornecedor com o id 2"));
        verify(fornecedorRepository, times(1)).existsById(2);
        verify(fornecedorRepository, never()).deleteById(anyInt());
    }

    @Test
    void fornecedorPorIdNaoEncontradoTeste() {
        when(fornecedorRepository.findById(99)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.fornecedorPorId(99));

        assertTrue(ex.getMessage().contains("Nao foi encontrado fornecedor com o id 99"));
        verify(fornecedorRepository, times(1)).findById(99);
    }
}
