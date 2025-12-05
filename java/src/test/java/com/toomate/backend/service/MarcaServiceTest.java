package com.toomate.backend.service;

import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Marca;
import com.toomate.backend.repository.FornecedorRepository;
import com.toomate.backend.repository.InsumoRepository;
import com.toomate.backend.repository.MarcaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class MarcaServiceTest {
    @Mock
    private MarcaRepository marcaRepository;
    @Mock
    private InsumoRepository insumoRepository;
    @Mock
    private FornecedorRepository fornecedorRepository;
    @InjectMocks
    private MarcaService marcaService;

    @Nested
    class CadastroTest{
        @Test
        @DisplayName("Deve cadastrar uma marca com sucesso!")
        void deveCadastrarUmaMarca(){
            MarcaRequestDto dto = new MarcaRequestDto("teste", 1, 2);
            Marca marca = MarcaMapperDto.toEntity(dto);
            marca.setInsumo(new Insumo());
            marca.setFornecedor(new Fornecedor());

            Mockito.when(marcaRepository.existsByNomeMarca(dto.getNome())).thenReturn(false);

            Mockito.when(insumoRepository.findById(dto.getFkInsumo())).thenReturn(Optional.of(new Insumo()));

            Mockito.when(fornecedorRepository.findById(dto.getFkFornecedor())).thenReturn(Optional.of(new Fornecedor()));

            Mockito.when(marcaRepository.save(any(Marca.class))).thenReturn(marca);

            Marca esperado = marcaService.cadastrar(dto);
            assertEquals(dto.getNome(), esperado.getNomeMarca());
            assertNotNull(esperado.getFornecedor());
        }

        @Test
        @DisplayName("Deve lançar exceção ao tentar cadastrar uma marca com nome duplicado")
        void deveLancarExcecaoQuandoNomeDuplicado(){
            MarcaRequestDto dto = new MarcaRequestDto("teste", 1, 1);

            Mockito.when(marcaRepository.existsByNomeMarca(dto.getNome())).thenReturn(true);

            assertThrows(RecursoExisteException.class, () -> marcaService.cadastrar(dto));
        }

        @Test
        @DisplayName("Deve lançar exceção quando insumo não existe")
        void deveLancarExcecaoQuandoInsumoNaoExiste(){
            MarcaRequestDto dto = new MarcaRequestDto("teste", 1, 1);

            Mockito.when(marcaRepository.existsByNomeMarca(dto.getNome())).thenReturn(false);

            Mockito.when(insumoRepository.findById(dto.getFkInsumo())).thenReturn(Optional.empty());

            assertThrows(EntidadeNaoEncontradaException.class, () -> marcaService.cadastrar(dto));
        }

        @Test
        @DisplayName("Deve lançar exceção quando fornecedor não existe")
        void deveLancarExcecaoQuandoFornecedorNaoExiste(){
            MarcaRequestDto dto = new MarcaRequestDto("teste", 1, 1);

            Mockito.when(marcaRepository.existsByNomeMarca(dto.getNome())).thenReturn(false);

            Mockito.when(insumoRepository.findById(dto.getFkInsumo())).thenReturn(Optional.of(new Insumo()));

            Mockito.when(fornecedorRepository.findById(dto.getFkFornecedor())).thenReturn(Optional.empty());

            assertThrows(EntidadeNaoEncontradaException.class, () -> marcaService.cadastrar(dto));
        }

        @Test
        @DisplayName("Deve lançar exceção quando a entrada é nula")
        void deveLancarExcecaoQuandoEntradaForNula(){
           assertThrows(EntradaInvalidaException.class, () -> marcaService.cadastrar(null));
        }
    }

    @Nested
    class ListarTest {
        @Test
        @DisplayName("Deve retornar a lista vazia")
        void deveRetornarListaVaziaTest(){
            List<Marca> esperado = new ArrayList<>();

            Mockito.when(marcaRepository.findAll()).thenReturn(esperado);

            List<Marca> recebido = marcaService.listar();

            assertTrue(recebido.isEmpty());
            assertEquals(esperado, recebido);
        }

        @Test
        @DisplayName("Deve retornar lista com objetos")
        void deveRetornarListaCheiaTest() {
            Marca marca = new Marca();
            List<Marca> esperado = List.of(marca);

            Mockito.when(marcaRepository.findAll()).thenReturn(esperado);

            List<Marca> recebido = marcaService.listar();

            assertFalse(recebido.isEmpty());
            assertEquals(esperado, recebido);

        }
    }

    @Nested
    class BuscarPorIdTest{
        @Test
        @DisplayName("Deve lançar exceção quando a marca não for encontrada")
        void deveLancarExcecaoQUandoMarcaNaoExiste(){
            Integer id = 1;
            Mockito.when(marcaRepository.findById(id)).thenReturn(Optional.empty());
            assertThrows(EntidadeNaoEncontradaException.class, () -> marcaService.marcaPorId(id));
        }

        @Test
        @DisplayName("Deve retornar a marca encontrada")
        void deveRetornarAMarcaEncontrada(){
            Integer id = 1;
            Marca esperado = new Marca();
            esperado.setNomeMarca("Teste");

            Mockito.when(marcaRepository.findById(id)).thenReturn(Optional.of(esperado));

            Marca recebido = marcaService.marcaPorId(id);

            assertEquals(esperado.getNomeMarca(), recebido.getNomeMarca());
        }
    }

    @Nested
    class AtualizarTeste{
        @Test
        @DisplayName("Deve lançar exceção quando a marca não for encontrada")
        void deveLancarExcecaoQUandoMarcaNaoExiste(){
            Integer id = 1;
            Marca marca = new Marca();
            Mockito.when(marcaRepository.existsById(id)).thenReturn(false);
            assertThrows(EntidadeNaoEncontradaException.class, () -> marcaService.atualizar(id, marca));
        }

        @Test
        @DisplayName("Deve atualizar uma marca com sucesso")
        void deveAtualizarUmaMarcaPorSucesso(){
            Integer id = 1;
            Marca marca = new Marca();
            marca.setNomeMarca("Teste");
            Marca esperado = new Marca();
            esperado.setNomeMarca("atualizado");

            Mockito.when(marcaRepository.existsById(id)).thenReturn(true);

            Mockito.when(marcaRepository.save(any(Marca.class))).thenReturn(esperado);

            Marca resultado = marcaService.atualizar(id, marca);

            assertEquals("atualizado", resultado.getNomeMarca());
        }

    }

    @Nested
    class DeletarTest{
        @Test
        @DisplayName("Deve deletar com sucesso uma marca")
        void deveDeletarUmaMarcaComSucesso(){
            Integer id = 1;
            Mockito.when(marcaRepository.existsById(id)).thenReturn(true);

            marcaService.deletar(id);

            Mockito.verify(marcaRepository, Mockito.times(1)).existsById(id);
            Mockito.verify(marcaRepository, Mockito.times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Deve lançar exceção quando marca não existe")
        void deveLancerExcecaoQuandoDeletarNaoExiste(){
            Integer id = 1;
            Mockito.when(marcaRepository.existsById(id)).thenReturn(false);

            assertThrows(EntidadeNaoEncontradaException.class, () -> marcaService.deletar(id));
        }
    }




}