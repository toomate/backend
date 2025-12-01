package com.toomate.backend.service;

import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InsumoServiceTest {

    @InjectMocks
    private InsumoService service;
    @Mock
    private InsumoRepository repository;

    @Nested
    class listar{
    @Test
    void DeveRetornarListaComElementos(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        Categoria carboidrato = new Categoria(1, "carboidrato");
        Categoria bebida = new Categoria(2, "bebida");
        insumos.add(new Insumo(1, "arroz", carboidrato, 3, "kg"));
        insumos.add(new Insumo(2, "café", bebida, 5, "ml"));
        when(repository.findAll()).thenReturn(insumos);
        //ACT
        List<Insumo> atual = repository.findAll();
        //ASSERT
        assertEquals(atual.getFirst(), insumos.getFirst());
        assertEquals(atual.getLast(), insumos.getLast());
    }

    @Test
    void DeveRetornarListaVazia(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        when(repository.findAll()).thenReturn(insumos);
        //ACT
        List<Insumo> atual = repository.findAll();
        //ASSERT
        assertTrue(atual.isEmpty());
    }
    }

    @Nested
    class listarPorNome{
    @Test
    void DeveRetornarContendoNome(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        Categoria bebida = new Categoria(1, "bebida");
        Insumo cafe =   new Insumo(1, "café", bebida, 5, "ml");
        insumos.add(cafe);
        when(repository.findByNomeContainingIgnoreCase("café")).thenReturn(insumos);
        //ACT
        List<Insumo> atual = repository.findByNomeContainingIgnoreCase("café");
        //ASSERT
        assertEquals(atual, insumos);
    }

    @Test
    void DeveRetornarListaVazia(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        when(repository.findByNomeContainingIgnoreCase("agua")).thenReturn(insumos);
        List<Insumo> atual = service.listarPorNome("agua");
        assertArrayEquals(insumos.toArray(), atual.toArray());
    }
    }

    @Nested
    class cadastrar{

        @Test
        void DeveRetornarInsumoCadastrado(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            when(repository.save(insumo)).thenReturn(insumo);
            Insumo atual = service.cadastrar(insumoRequestDto, categoria);
            assertEquals(atual, insumo);
        }

        @Test
        void DeveLancarExcecaoEntradaInvalidaComInsumoRequestNull(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            assertThrows(EntradaInvalidaException.class, ()->service.cadastrar(null, categoria));
        }

        @Test
        void DeveLancarExcecaoEntradaInvalidaComCategoriaNull(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            assertThrows(EntradaInvalidaException.class, ()->service.cadastrar(insumoRequestDto, null));
        }

        @Test
        void DeveLancarExcecaoEntradaInvalidaComInsumoRequestNullECategoriaNull(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            assertThrows(EntradaInvalidaException.class, ()->service.cadastrar(null, null));
        }
    }

    @Nested
    class deletar{

        //@Test

     @Test
        void DeveLancarExcecaoNaoEncontrada(){
         when(repository.existsById(1)).thenReturn(false);
         assertThrows(EntidadeNaoEncontradaException.class, ()->service.deletar(1));
     }
    }

    @Nested
    class atualizar{

        @Test
        void DeveRetornarInsumoAtualizado(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            when(repository.save(insumo)).thenReturn(insumo);
            Insumo atual = service.atualizar(insumo.getIdInsumo(), insumo);
            assertEquals(atual, insumo);
        }

        @Test
        void DeveLancarExcecaoNaoEncontrada(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            when(repository.existsById(1)).thenReturn(false);
            assertThrows(EntidadeNaoEncontradaException.class, ()->service.atualizar(1, insumo));
        }
    }

    @Nested
    class existePorNome{

        @Test
        void DeveRetornarTrue(){
            String nome = "lucas";
            Boolean esperado = true;
            when(repository.existsByNome(nome)).thenReturn(true);
            Boolean atual = service.existePorNome(nome);
            assertEquals(esperado, atual);
        }

        @Test
        void DeveRetornarFalse(){
            String nome = "lucas";
            Boolean esperado = false;
            when(repository.existsByNome(nome)).thenReturn(false);
            Boolean atual = service.existePorNome(nome);
            assertEquals(esperado, atual);
        }
    }

    @Nested
    @DisplayName("BuscarInsumoPorId")
    class insumoPorId{

        @Test
        void DeveRetornarInsumo(){
            InsumoRequestDto insumoRequestDto = new InsumoRequestDto();
            insumoRequestDto.setNome("aaa");
            insumoRequestDto.setFkCategoria(1);
            insumoRequestDto.setQtdMinima(5);
            insumoRequestDto.setUnidadeMedida("kg");
            Categoria categoria = new Categoria();
            categoria.setIdCategoria(1);
            categoria.setNome("lucas");
            Insumo insumo = new Insumo();
            insumo.setIdInsumo(1);
            insumo.setCategoria(categoria);
            insumo.setNome(insumoRequestDto.getNome());
            insumo.setQtdMinima(insumoRequestDto.getQtdMinima());
            insumo.setUnidadeMedida(insumoRequestDto.getUnidadeMedida());
            when(repository.findById(1)).thenReturn(Optional.of(insumo));
            Insumo atual = service.insumoPorId(1);
            assertEquals(insumo, atual);
        }

        @Test
        void DeveLancarExcecaoEntidadeNaoEncontrada(){
            when(repository.findById(1)).thenReturn(Optional.empty());
            assertThrows(EntidadeNaoEncontradaException.class, ()-> service.insumoPorId(1));
        }

    }

}