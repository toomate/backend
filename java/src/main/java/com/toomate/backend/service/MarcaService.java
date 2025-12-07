package com.toomate.backend.service;

import com.toomate.backend.dto.insumo.InsumoRequestDto;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaService {
    private final MarcaRepository marcaRepository;
    private final InsumoRepository insumoRepository;
    private final FornecedorRepository fornecedorRepository;

    public MarcaService(MarcaRepository marcaRepository, InsumoRepository insumoRepository, FornecedorRepository fornecedorRepository) {
        this.marcaRepository = marcaRepository;
        this.insumoRepository = insumoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<Marca> listar() {
        return marcaRepository.findAll();
    }

    public List<Marca> listarPorNome(String nome) {
        return marcaRepository.findByNomeMarcaContainingIgnoreCase(nome);
    }

    public Marca cadastrar(MarcaRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("A marca não pode ser nula!");
        }

        if (marcaRepository.existsByNomeMarca(request.getNome())){
            throw new RecursoExisteException("Já existe uma marca com este nome!");
        }

        Insumo insumo = insumoRepository.findById(request.getFkInsumo()).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um insumo com o id %d", request.getFkInsumo())));
        Fornecedor fornecedor = fornecedorRepository.findById(request.getFkFornecedor()).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", request.getFkFornecedor())));

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
