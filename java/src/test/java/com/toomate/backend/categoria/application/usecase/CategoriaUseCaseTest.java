package com.toomate.backend.categoria.application.usecase;

import com.toomate.backend.categoria.application.port.out.CategoriaGateway;
import com.toomate.backend.categoria.domain.exception.CategoriaInvalidaException;
import com.toomate.backend.categoria.domain.exception.CategoriaJaExisteException;
import com.toomate.backend.categoria.domain.exception.CategoriaNaoEncontradaException;
import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoriaUseCaseTest {

    @Mock
    private CategoriaGateway categoriaGateway;

    @InjectMocks
    private CategoriaUseCase categoriaUseCase;

    @Test
    @DisplayName("Deve lancar excecao ao cadastrar categoria com nome vazio")
    void deveLancarExcecaoQuandoNomeInvalido() {
        assertThrows(CategoriaInvalidaException.class, () -> categoriaUseCase.cadastrar("   ", true));
        verify(categoriaGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve lancar conflito quando nome ja existir")
    void deveLancarConflitoQuandoNomeJaExiste() {
        when(categoriaGateway.existsByNomeIgnoreCase("Massas")).thenReturn(true);

        assertThrows(CategoriaJaExisteException.class, () -> categoriaUseCase.cadastrar("Massas", false));
        verify(categoriaGateway, never()).save(any());
    }

    @Test
    @DisplayName("Deve cadastrar categoria com sucesso")
    void deveCadastrarComSucesso() {
        CategoriaDomain salva = new CategoriaDomain(10, "Massas", true);
        when(categoriaGateway.existsByNomeIgnoreCase("Massas")).thenReturn(false);
        when(categoriaGateway.save(any(CategoriaDomain.class))).thenReturn(salva);

        CategoriaDomain resultado = categoriaUseCase.cadastrar("Massas", true);

        assertEquals(10, resultado.getId());
        assertEquals("Massas", resultado.getNome());
        assertEquals(Boolean.TRUE, resultado.getRotatividade());
        verify(categoriaGateway).save(any(CategoriaDomain.class));
    }

    @Test
    @DisplayName("Deve lancar excecao ao atualizar categoria inexistente")
    void deveLancarExcecaoAoAtualizarCategoriaInexistente() {
        when(categoriaGateway.findById(99)).thenReturn(Optional.empty());

        assertThrows(CategoriaNaoEncontradaException.class, () -> categoriaUseCase.atualizar(99, "Verduras", true));
        verify(categoriaGateway, never()).save(any());
    }
}
