package com.toomate.backend.service;

import com.toomate.backend.dto.categoria.CategoriaMapperDto;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
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
            throw new EntradaInvalidaException("O fornecedor não pode ser nulo!");
        }

        Categoria categoria = CategoriaMapperDto.toEntity(request);

        return categoriaRepository.save(categoria);
    }

    public Boolean existePorNome(String nome) {
        return categoriaRepository.existsByNome(nome);
    }

    public Boolean existePorId(Integer id) {
        return categoriaRepository.existsById(id);
    }

    public Categoria categoriaPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada categoria com o id %d", id)));
    }

    public Categoria atualizar(Integer id, Categoria categoria) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado uma categoria com o id %d", id));
        }

        categoria.setIdCategoria(id);
        return categoriaRepository.save(categoria);
    }

    public void deletar(Integer id) {
        if (!categoriaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um fornecedor com o id %d", id));
        }

        categoriaRepository.deleteById(id);
    }
}