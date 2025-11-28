package com.toomate.backend.service;

import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import jakarta.validation.constraints.AssertTrue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InsumoServiceTest {

    @InjectMocks
    private InsumoService service;
    @Mock
    private InsumoRepository repository;
    @Test
    void DeveRetornarListaComElementos(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        Categoria carboidrato = new Categoria(1, "carboidrato");
        Categoria bebida = new Categoria(2, "bebida");
        insumos.add(new Insumo(1, "arroz", carboidrato, 3, "kg"));
        insumos.add(new Insumo(2, "café", bebida, 5, "ml"));
        Mockito.when(repository.findAll()).thenReturn(insumos);
        //ACT
        List<Insumo> esperado = repository.findAll();
        //ASSERT
        assertEquals(esperado.getFirst(), insumos.getFirst());
        assertEquals(esperado.getLast(), insumos.getLast());
    }

    @Test
    void DeveRetornarListaVazia(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(insumos);
        //ACT
        List<Insumo> esperado = repository.findAll();
        //ASSERT
        assertTrue(esperado.isEmpty());
    }

    @Test
    void DeveRetornarContendoNome(){
        //Arrange
        List<Insumo> insumos = new ArrayList<>();
        Categoria bebida = new Categoria(1, "bebida");
        Insumo cafe =   new Insumo(1, "café", bebida, 5, "ml");
        insumos.add(cafe);
        Mockito.when(repository.findByNomeContainingIgnoreCase("café")).thenReturn(insumos);
        //ACT
        List<Insumo> esperado = repository.findByNomeContainingIgnoreCase("café");
        //ASSERT
        assertEquals(esperado, insumos);
    }

}