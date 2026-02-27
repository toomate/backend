package com.toomate.backend.service;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.mapper.fornecedor.FornecedorMapper;
import com.toomate.backend.model.Fornecedor;
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
        return fornecedorRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um fornecedor com o id %d", id))
        );
    }

    public List<Fornecedor> filtrar(String razaoSocial) {
        if (razaoSocial == null || razaoSocial.isBlank()) {
            return fornecedorRepository.findAll();
        }
        return fornecedorRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial.trim());
    }

    public Fornecedor cadastrar(FornecedorRequestDto request) {
        validarRequest(request);
        String razaoSocialNormalizada = normalizarTexto(request.getRazaoSocial());

        if (fornecedorRepository.existsByRazaoSocialIgnoreCase(razaoSocialNormalizada)) {
            throw new RecursoExisteException("Ja existe um fornecedor com essa razao social.");
        }

        Fornecedor fornecedor = FornecedorMapper.toEntity(request);
        fornecedor.setRazaoSocial(razaoSocialNormalizada);
        fornecedor.setLink(normalizarTexto(request.getLink()));
        fornecedor.setTelefone(normalizarTexto(request.getTelefone()));

        return fornecedorRepository.save(fornecedor);
    }

    public Fornecedor atualizar(Integer id, FornecedorRequestDto request) {
        validarRequest(request);
        Fornecedor atual = retornarPeloId(id);
        String novaRazaoSocial = normalizarTexto(request.getRazaoSocial());

        if (!atual.getRazaoSocial().equalsIgnoreCase(novaRazaoSocial)
                && fornecedorRepository.existsByRazaoSocialIgnoreCase(novaRazaoSocial)) {
            throw new RecursoExisteException("Ja existe um fornecedor com essa razao social.");
        }

        atual.setRazaoSocial(novaRazaoSocial);
        atual.setLink(normalizarTexto(request.getLink()));
        atual.setTelefone(normalizarTexto(request.getTelefone()));

        return fornecedorRepository.save(atual);
    }

    public void deletar(Integer id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado um fornecedor com o id %d", id));
        }
        fornecedorRepository.deleteById(id);
    }

    public Fornecedor fornecedorPorId(Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado fornecedor com o id %d", id))
        );
    }

    private void validarRequest(FornecedorRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("O fornecedor nao pode ser nulo.");
        }
        if (request.getRazaoSocial() == null || request.getRazaoSocial().isBlank()) {
            throw new EntradaInvalidaException("A razao social do fornecedor nao pode ser vazia.");
        }
        if (request.getLink() == null || request.getLink().isBlank()) {
            throw new EntradaInvalidaException("O link do fornecedor nao pode ser vazio.");
        }
        if (request.getTelefone() == null || request.getTelefone().isBlank()) {
            throw new EntradaInvalidaException("O telefone do fornecedor nao pode ser vazio.");
        }
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim();
    }
}
