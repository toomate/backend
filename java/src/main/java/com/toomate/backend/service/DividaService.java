package com.toomate.backend.service;

import com.toomate.backend.dto.divida.DividaRequestDto;
import com.toomate.backend.dto.divida.DividaResponseModalDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.Divida.DividaMapper;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.model.Divida;
import com.toomate.backend.repository.ClienteRepository;
import com.toomate.backend.repository.DividaRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DividaService {

    private final DividaRepository dividaRepository;
    private final ClienteRepository clienteRepository;

    public DividaService(DividaRepository dividaRepository, ClienteRepository clienteRepository) {
        this.dividaRepository = dividaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Divida cadastrar(DividaRequestDto divida) {
        Optional<Cliente> cliente = clienteRepository.findById(divida.getClienteId());
        if (cliente.isEmpty()) {
            throw new EntradaInvalidaException("Cliente não encontrado");
        }
        if (divida.getDataCompra().isAfter(LocalDate.now())) {
            throw new EntradaInvalidaException("Divida com data de compra inválida");
        }
        if (divida.getValor() <= 0) {
            throw new EntradaInvalidaException("Divida com valor invalido");
        }
        return DividaMapper.toEntity(divida, cliente.get());
    }

    public List<DividaResponseModalDto> listarModal() {
        return dividaRepository.findAll()
                .stream()
                .map(DividaMapper::toResponsesModal)
                .toList();
    }

    public Divida atualizarEstado(Integer idDivida) {
        if (dividaRepository.existsById(idDivida)) {
            Divida divida = dividaRepository.findById(idDivida).get();
            divida.setPago(true);
            divida.setDataPagamento(LocalDate.now());
            dividaRepository.save(divida);
        }
        throw new EntidadeNaoEncontradaException("Divida não encontrada");
    }

    public Boolean existePorId(Integer id) {
        return dividaRepository.existsById(id);
    }

    public Divida atualizar(Integer idDivida, DividaRequestDto dividaRequestDto) {
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

        return dividaRepository.save(divida);
    }
}
