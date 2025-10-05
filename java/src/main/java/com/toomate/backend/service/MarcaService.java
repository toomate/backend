package com.toomate.backend.service;

import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Marca;
import com.toomate.backend.repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;

    public MarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    public List<Marca> listar() {
        return marcaRepository.findAll();
    }

    public List<Marca> listarPorNome(String nome) {
        return marcaRepository.findByNomeMarcaContainingIgnoreCase(nome);
    }

    public Marca cadastrar(MarcaRequestDto request, Fornecedor fornecedor, Insumo insumo) {
        if (request == null) {
            throw new EntradaInvalidaException("A marca não pode ser nula!");
        }

        if (fornecedor == null) {
            throw new EntradaInvalidaException("O fornecedor não pode ser nulo!");
        }

        if (insumo == null) {
            throw new EntradaInvalidaException("O insumo não pode ser nulo!");
        }

        Marca marca = MarcaMapperDto.toEntity(request, insumo, fornecedor);
        return marcaRepository.save(marca);
    }

    public void deletar(Integer id) {
        if (!marcaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id));
        }

        marcaRepository.deleteById(id);
    }

    public Marca atualizar(Integer id, Marca marca) {
        if (!marcaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id));
        }

        marca.setIdMarca(id);
        return marcaRepository.save(marca);
    }

    public Boolean existePorNome(String nome) {
        return marcaRepository.existsByNomeMarca(nome);
    }

    public Boolean existePorId(Integer id) {
        return marcaRepository.existsById(id);
    }

    public Marca marcaPorId(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id)));
    }
}
