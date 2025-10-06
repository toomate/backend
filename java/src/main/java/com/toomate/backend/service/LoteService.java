package com.toomate.backend.service;

import com.toomate.backend.dto.lote.LoteMapperDto;
import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.LoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoteService {
    private final LoteRepository loteRepository;

    public LoteService(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public Lote listarPorId(Integer id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado lote com o id %d", id)));
    }

    public Lote cadastrar(LoteRequestDto request, Usuario usuario, Marca marca) {
        if (request == null) {
            throw new EntradaInvalidaException("O lote não pode ser nula!");
        }

        if (usuario == null) {
            throw new EntradaInvalidaException("O fornecedor não pode ser nulo!");
        }

        if (marca == null) {
            throw new EntradaInvalidaException("O insumo não pode ser nulo!");
        }

        Lote lote = LoteMapperDto.toEntity(request, usuario, marca);
        return loteRepository.save(lote);
    }

    public void deletar(Integer id) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        loteRepository.deleteById(id);
    }

    public Lote atualizar(Integer id, Lote lote) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        lote.setIdLote(id);
        return loteRepository.save(lote);
    }

    public Boolean existePorId(Integer id) {
        return loteRepository.existsById(id);
    }

    public Lote lotePorId(Integer id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id)));
    }
}
