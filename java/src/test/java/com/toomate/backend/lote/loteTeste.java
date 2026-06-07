package com.toomate.backend.lote;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.InsumoRepository;
import com.toomate.backend.repository.LoteRepository;
import com.toomate.backend.service.LoteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class loteTeste {

    @Mock
    private LoteRepository loteRepository;
    @Mock
    private InsumoRepository insumoRepository;

    @Mock
    private ProducerRabbitMQ producerRabbitMQ;

    @Mock
    public AuditService auditService;

    @InjectMocks
    public LoteService loteService;


    @BeforeEach
    void setupAutenticacao() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("operador.teste", "senha")
        );
    }

    @AfterEach
    void limparAutenticacao() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve cadastrar o lote quando ele for criado corretamente.")
    public void validarCadastroCorreto(){
//        negarNotif();
        Lote lote = criarLoteValido();
        Insumo insumo = lote.getMarca().getInsumo();

        when(insumoRepository.findById(insumo.getIdInsumo()))
                .thenReturn(Optional.of(insumo));
        when(loteRepository.save(any(Lote.class)))
                .thenReturn(lote);
        when(loteRepository.getEstoqueInsumo(anyInt()))
                .thenReturn(100.0);

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
        Insumo insumo = new Insumo(1, "arroz", null, 20, true, true);
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
