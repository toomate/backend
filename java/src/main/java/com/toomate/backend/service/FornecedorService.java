package com.toomate.backend.service;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.fornecedor.FornecedorMapper;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.FornecedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorService(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor retornarPeloId(Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", id)));
    }

    public List<Fornecedor> filtrar(String razaoSocial) {
        return fornecedorRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);
    }

    public Fornecedor cadastrar(FornecedorRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("O fornecedor não pode ser nulo!");
        }

        Fornecedor fornecedor = FornecedorMapper.toEntity(request);

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Integer id, Fornecedor fornecedor) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", id));
        }
        fornecedor.setId(id);
        return fornecedorRepository.save(fornecedor);
    }

    public void deletar(Integer id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", id));
        }
        fornecedorRepository.deleteById(id);
    }

    public Fornecedor fornecedorPorId(Integer id) {
        return fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado fornecedor com o id %d", id)));
    }
}

