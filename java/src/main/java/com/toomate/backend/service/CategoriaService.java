package com.toomate.backend.service;

import com.toomate.backend.mapper.categoria.CategoriaMapper;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService  {
    private final CategoriaRepository categoriaRepository;

    public CategoriaService (CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public List<Categoria> listarPorNome(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Categoria cadastrar(CategoriaRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("A categoria não pode ser nula!");
        }

        if (categoriaRepository.existsByNomeIgnoreCase(request.getNome())) {
            throw new RecursoExisteException("Já existe uma categoria cadastrada com esse nome.");
        }

        Categoria categoria = CategoriaMapper.toEntity(request);

        return categoriaRepository.save(categoria);
    }

    public Boolean existePorNome(String nome) {
        return categoriaRepository.existsByNomeIgnoreCase(nome);
    }

    public Boolean existePorId(Integer id) {
        return categoriaRepository.existsById(id);
    }

    public Categoria categoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada categoria com o id %d", id)));
    }

    public Categoria atualizar(Integer id, CategoriaRequestDto request) {
        Categoria existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrada categoria com o id %d", id)));

        String nomeAtual = existente.getNome() == null ? "" : existente.getNome();
        if (!nomeAtual.equalsIgnoreCase(request.getNome()) && categoriaRepository.existsByNomeIgnoreCase(request.getNome())) {
            throw new RecursoExisteException("Já existe uma categoria cadastrada com esse nome.");
        }

        existente.setNome(request.getNome());
        return categoriaRepository.save(existente);
    }

    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", id));
        }

        categoriaRepository.deleteById(id);
    }
}
