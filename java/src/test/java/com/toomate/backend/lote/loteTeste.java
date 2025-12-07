package com.toomate.backend.lote;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.EnviarNotificacao;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.LoteRepository;
import com.toomate.backend.service.LoteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class loteTeste {

    @Mock
    private LoteRepository loteRepository;

    @Mock
    private EnviarNotificacao enviarNotificacao;

    @InjectMocks
    public LoteService loteService;

    @Test
    @DisplayName("Deve cadastrar o lote quando ele for criado corretamente.")
    public void validarCadastroCorreto(){
//        negarNotif();
        Lote lote = criarLoteValido();

        when(loteRepository.save(any(Lote.class)))
                .thenReturn(lote);

        lote.setQuantidadeMedida(3.0);
        lote.setPrecoUnitario(20.0);

        assertEquals(lote, loteService.cadastrar(lote));
    }

    @Test
    @DisplayName("Deve jogar excessão ao cadastrar lote com quantidade invalida")
    public void validarCadastroQtdNegativa(){
        Lote lote = criarLoteValido();

            lote.setQuantidadeMedida(-1.0);
            assertThrows(EntradaInvalidaException.class,
                    () -> loteService.cadastrar(lote));

    }

    @Test
    @DisplayName("Deve jogar excessão ao cadastrar lote com preço inválido")
    public void validarCadastroPrecoInvalido(){
        Lote lote = criarLoteValido();

        lote.setQuantidadeMedida(10.0);
        lote.setPrecoUnitario(999.9);
        assertThrows(EntradaInvalidaException.class,
                () -> loteService.cadastrar(lote));
    }

    @Test
    @DisplayName("Deve jogar excessão ao cadastrar lote com data inválida.")
    public void validarDataVencimento(){
        Lote lote = criarLoteValido();
        lote.setQuantidadeMedida(10.0);
        lote.setPrecoUnitario(999.9);
        lote.setDataValidade(LocalDate.now().minusDays(20));

        assertThrows(EntradaInvalidaException.class,
                () -> loteService.cadastrar(lote));
    }

    public Lote criarLoteValido(){
        Insumo insumo = new Insumo(1, "arroz", null, 20, "kilos");
        Marca marca = new Marca();
        marca.setInsumo(insumo);
        Usuario usuario = new Usuario();


        Lote lote = new Lote();
        lote.setUsuario(usuario);
        lote.setMarca(marca);

        return lote;
    }


    public void negarNotif(){
        Insumo insumo = new Insumo();
        when(loteRepository.getEstoqueInsumo(insumo.getIdInsumo()))
                .thenReturn(100.0);
    }

}
