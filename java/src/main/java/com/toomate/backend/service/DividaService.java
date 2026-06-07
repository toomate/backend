package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.divida.DividaRequestDto;
import com.toomate.backend.dto.divida.DividaResponseDto;
import com.toomate.backend.dto.divida.DividaResponseModalDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.Divida.DividaMapper;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.model.Divida;
import com.toomate.backend.repository.ClienteRepository;
import com.toomate.backend.repository.DividaRepository;
import com.toomate.backend.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DividaService {

    private final DividaRepository dividaRepository;
    private final ClienteRepository clienteRepository;
    private final AuditService auditService;
    private final UsuarioRepository usuarioRepository;

    public DividaService(DividaRepository dividaRepository, ClienteRepository clienteRepository, AuditService auditService, UsuarioRepository usuarioRepository) {
        this.dividaRepository = dividaRepository;
        this.clienteRepository = clienteRepository;
        this.auditService = auditService;
        this.usuarioRepository = usuarioRepository;
    }

    public DividaResponseDto cadastrar(DividaRequestDto divida) {
        Optional<Cliente> cliente = clienteRepository.findById(divida.getIdCliente());
        if (cliente.isEmpty()) {
            throw new EntradaInvalidaException("Cliente não encontrado");
        }
        if (divida.getDataCompra().isAfter(LocalDate.now())) {
            throw new EntradaInvalidaException("Divida com data de compra inválida");
        }
        if (divida.getValor() <= 0) {
            throw new EntradaInvalidaException("Divida com valor invalido");
        }
        Divida dividaParaCadastrar = DividaMapper.toEntity(divida, cliente.get());

        dividaRepository.save(dividaParaCadastrar);
        auditService.registrar(getUsuarioLogado(), "CADASTRO", "DIVIDA",
                String.format("Cadastrou dívida de R$%.2f para cliente ID: %d", divida.getValor(), divida.getIdCliente()));

        return DividaMapper.toResponse(dividaParaCadastrar, cliente.get());
    }

    public List<DividaResponseModalDto> listarModal() {
        return dividaRepository.findAll()
                .stream()
                .map(DividaMapper::toResponsesModal)
                .toList();
    }

    public Page<Divida> listarComPaginacao(Integer pagina, Integer tamanho) {
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);

        return dividaRepository.findAll(pgRequest);
    }

    public DividaResponseDto atualizarEstado(Integer idDivida) {
        if (dividaRepository.existsById(idDivida)) {
            Divida divida = dividaRepository.findById(idDivida).get();
            divida.setPago(true);
            divida.setDataPagamento(LocalDate.now());
            Divida dividaAtualizada = dividaRepository.save(divida);
            auditService.registrar(getUsuarioLogado(), "PAGAMENTO", "DIVIDA", "Marcou dívida ID: " + idDivida + " como paga");
            return DividaMapper.toResponse(dividaAtualizada, dividaAtualizada.getCliente());
        }
        throw new EntidadeNaoEncontradaException("Divida não encontrada");
    }

    public DividaResponseDto buscarPorId(Integer id) {
        Divida divida = dividaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrada uma dívida com esse id"));

        return DividaMapper.toResponse(divida, divida.getCliente());
    }


    public Boolean existePorId(Integer id) {
        return dividaRepository.existsById(id);
    }

    public DividaResponseDto atualizar(Integer idDivida, DividaRequestDto dividaRequestDto) {
        Divida divida = dividaRepository.findById(idDivida).get();
        if (!Objects.isNull(dividaRequestDto.getPago())) {
            divida.setPago(dividaRequestDto.getPago());
        }
        if (!Objects.isNull(dividaRequestDto.getValor())) {
            divida.setValor(dividaRequestDto.getValor());
        }
        if (!Objects.isNull(dividaRequestDto.getDataCompra())) {
            divida.setDataCompra(dividaRequestDto.getDataCompra());
        }
        if (!Objects.isNull(dividaRequestDto.getPedido())) {
            divida.setPedido(dividaRequestDto.getPedido());
        }
        if (!Objects.isNull(dividaRequestDto.getDataPagamento())) {
            divida.setDataPagamento(dividaRequestDto.getDataPagamento());
        }

        divida.setIdDivida(idDivida);

        Divida dividaAtualizada = dividaRepository.save(divida);

        return DividaMapper.toResponse(dividaAtualizada, dividaAtualizada.getCliente());
    }

    public List<String> listarPedidos() {
        return dividaRepository.listarPedidos();
    }

    private String getUsuarioLogado() {
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }
}
