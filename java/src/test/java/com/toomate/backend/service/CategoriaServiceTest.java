package com.toomate.backend.service;

import com.sun.source.tree.ModuleTree;
import com.toomate.backend.dto.categoria.CategoriaMapperDto;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.repository.CategoriaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;
    @InjectMocks
    private CategoriaService categoriaService;

    @Nested
    class CadastrarTest {
        @Test
        @DisplayName("Deve lançar exceção quando a entrada de dados for nula")
        void deveLancarExcecaoQuandoEntradaForNula() {
            assertThrows(EntradaInvalidaException.class, () -> categoriaService.cadastrar(null));
        }

        @Test
        @DisplayName("Deve cadastrar uma caegoria com sucesso")
        void deveCadastrarUmaCategoriaComSucesso() {
            CategoriaRequestDto dto = new CategoriaRequestDto("teste");
            Categoria esperado = CategoriaMapperDto.toEntity(dto);

            Mockito.when(categoriaRepository.save(any(Categoria.class))).thenReturn(esperado);

            Categoria recebido = categoriaService.cadastrar(dto);

            assertEquals(dto.getNome(), recebido.getNome());
        }
    }

    @Nested
    class ListarTest {
        @Test
        @DisplayName("Deve retornar lista vazia")
        void deveRetornarListaVaziaTest() {
            List<Categoria> esperado = new ArrayList<>();

            Mockito.when(categoriaRepository.findAll()).thenReturn(esperado);

            List<Categoria> recebido = categoriaService.listar();

            assertTrue(recebido.isEmpty());
            assertEquals(esperado, recebido);
        }

        @Test
        @DisplayName("Deve retornar lista com objetos")
        void deveRetornarListaComObjetosTest() {
            Categoria categoria = new Categoria();
            List<Categoria> esperado = List.of(categoria);

            Mockito.when(categoriaRepository.findAll()).thenReturn(esperado);

            List<Categoria> recebido = categoriaService.listar();

            assertFalse(recebido.isEmpty());
            assertEquals(esperado, recebido);
        }
    }

    @Nested
    class BuscarPorIdTest {
        @Test
        @DisplayName("Deve lançar exceção quando categoria não existir")
        void deveLancarExcecaoQuandoCategoriaNaoExistir() {
            Integer id = 1;
            Mockito.when(categoriaRepository.findById(anyInt())).thenReturn(Optional.empty());

            assertThrows(EntidadeNaoEncontradaException.class, () -> categoriaService.categoriaPorId(id));
        }

        @Test
        @DisplayName("Deve retornar a entidade dona do ID")
        void deveRetornarEntidadeDonaDoId() {
            Integer id = 1;
            Categoria esperado = new Categoria();
            esperado.setNome("Teste");

            Mockito.when(categoriaRepository.findById(anyInt())).thenReturn(Optional.of(esperado));

            Categoria recebido = categoriaService.categoriaPorId(id);

            assertEquals(esperado.getNome(), recebido.getNome());
        }
    }

    @Nested
    class AtualizarTest {
        @Test
        @DisplayName("Deve lançar exceção quando a categoria não for encontrada")
        void deveLancarExcecaoQuandoCategoriaNaoExistir() {
            Integer id = 1;
            Mockito.when(categoriaRepository.existsById(id)).thenReturn(false);

            assertThrows(EntidadeNaoEncontradaException.class, () -> categoriaService.atualizar(id, new Categoria()));
        }

        @Test
        @DisplayName("Deve atualizar com sucesso uma categoria")
        void deveAtualizarComSucessoUmaCategoria() {
            Integer id = 1;
            Categoria categoria = new Categoria();
            Categoria esperado = new Categoria();
            esperado.setNome("atualizado");

            Mockito.when(categoriaRepository.existsById(id)).thenReturn(true);

            Mockito.when(categoriaRepository.save(any(Categoria.class))).thenReturn(esperado);

            Categoria recebido = categoriaService.atualizar(id, categoria);

            assertEquals("atualizado", recebido.getNome());
        }
    }

    @Nested
    class DeletarTest {
        @Test
        @DisplayName("Deve lançar exceção quando categoria não for encontrada")
        void deveLancarExcecaoQuandoCategoriaNaoForEncontrada() {
            Integer id = 1;

            Mockito.when(categoriaRepository.existsById(id)).thenReturn(false);

            assertThrows(EntidadeNaoEncontradaException.class, () -> categoriaService.deletar(id));
        }

        @Test
        @DisplayName("Deve deletar uma categoria com sucesso")
        void deveDeletarUmaCategoriaComSucesso(){
            Integer id = 1;

            Mockito.when(categoriaRepository.existsById(id)).thenReturn(true);

            categoriaService.deletar(id);

            Mockito.verify(categoriaRepository, Mockito.times(1)).existsById(id);
            Mockito.verify(categoriaRepository, Mockito.times(1)).deleteById(id);
        }
    }




}