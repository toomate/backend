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
        String telefoneNormalizado = normalizarTelefone(request.getTelefone());
        fornecedor.setTelefone(telefoneNormalizado);
        fornecedor.setLink(gerarLinkWhatsapp(telefoneNormalizado));

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
        String telefoneNormalizado = normalizarTelefone(request.getTelefone());
        atual.setTelefone(telefoneNormalizado);
        atual.setLink(gerarLinkWhatsapp(telefoneNormalizado));

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
        if (request.getTelefone() == null || request.getTelefone().isBlank()) {
            throw new EntradaInvalidaException("O telefone do fornecedor nao pode ser vazio.");
        }
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String normalizarTelefone(String telefone) {
        String somenteDigitos = telefone == null ? "" : telefone.replaceAll("\\D", "");
        if (somenteDigitos.isBlank()) {
            throw new EntradaInvalidaException("O telefone do fornecedor nao pode ser vazio.");
        }
        if (somenteDigitos.length() == 10 || somenteDigitos.length() == 11) {
            return "55" + somenteDigitos;
        }
        if (somenteDigitos.startsWith("55") && (somenteDigitos.length() == 12 || somenteDigitos.length() == 13)) {
            return somenteDigitos;
        }
        throw new EntradaInvalidaException("Telefone invalido. Informe DDD + numero.");
    }

    private String gerarLinkWhatsapp(String telefoneNormalizado) {
        return "https://wa.me/" + telefoneNormalizado;
    }
}
