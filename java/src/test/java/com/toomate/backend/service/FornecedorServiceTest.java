package com.toomate.backend.service;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    private Fornecedor exemploFornecedor;

    @BeforeEach
    void setup() {
        exemploFornecedor = new Fornecedor();
        exemploFornecedor.setId(1);
        exemploFornecedor.setLink("https://web.whatsapp.com/");
        exemploFornecedor.setRazaoSocial("Atacado São Paulo");
        exemploFornecedor.setTelefone("11987654321");
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

        assertTrue(ex.getMessage().contains("Não foi encontrado um fornecedor com o id 2"));
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
    void cadastrarSucessoTeste() {
        FornecedorRequestDto request = new FornecedorRequestDto(
                exemploFornecedor.getLink(), exemploFornecedor.getRazaoSocial(), exemploFornecedor.getTelefone());

        Fornecedor salvo = new Fornecedor();
        salvo.setId(5);
        salvo.setLink(request.getLink());
        salvo.setRazaoSocial(request.getRazaoSocial());
        salvo.setTelefone(request.getTelefone());

        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(salvo);

        Fornecedor resultado = fornecedorService.cadastrar(request);

        assertNotNull(resultado);
        assertEquals(5, resultado.getId());
        assertEquals(request.getRazaoSocial(), resultado.getRazaoSocial());
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void cadastrarEntradaNulaTeste() {
        EntradaInvalidaException ex = assertThrows(EntradaInvalidaException.class,
                () -> fornecedorService.cadastrar(null));

        assertTrue(ex.getMessage().contains("O fornecedor não pode ser nulo!"));
        verify(fornecedorRepository, never()).save(any());
    }

    @Test
    void atualizarSucessoTeste() {
        Fornecedor atualizacao = new Fornecedor();
        atualizacao.setLink("https://novo.link/");
        atualizacao.setRazaoSocial("Novo Nome");
        atualizacao.setTelefone("11999999999");

        when(fornecedorRepository.existsById(1)).thenReturn(true);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Fornecedor resultado = fornecedorService.atualizar(1, atualizacao);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Novo Nome", resultado.getRazaoSocial());
        verify(fornecedorRepository, times(1)).existsById(1);
        verify(fornecedorRepository, times(1)).save(any(Fornecedor.class));
    }

    @Test
    void atualizarNaoEncontradoTeste() {
        when(fornecedorRepository.existsById(2)).thenReturn(false);

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.atualizar(2, new Fornecedor()));

        assertTrue(ex.getMessage().contains("Não foi encontrado um fornecedor com o id 2"));
        verify(fornecedorRepository, times(1)).existsById(2);
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

        assertTrue(ex.getMessage().contains("Não foi encontrado um fornecedor com o id 2"));
        verify(fornecedorRepository, times(1)).existsById(2);
        verify(fornecedorRepository, never()).deleteById(anyInt());
    }

    @Test
    void fornecedorPorIdNaoEncontradoTeste() {
        when(fornecedorRepository.findById(99)).thenReturn(Optional.empty());

        EntidadeNaoEncontradaException ex = assertThrows(EntidadeNaoEncontradaException.class,
                () -> fornecedorService.fornecedorPorId(99));

        assertTrue(ex.getMessage().contains("Não foi encontrado fornecedor com o id 99"));
        verify(fornecedorRepository, times(1)).findById(99);
    }
}