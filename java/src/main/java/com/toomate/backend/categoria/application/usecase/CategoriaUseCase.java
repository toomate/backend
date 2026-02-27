package com.toomate.backend.categoria.application.usecase;

import com.toomate.backend.categoria.application.port.out.CategoriaGateway;
import com.toomate.backend.categoria.domain.exception.CategoriaInvalidaException;
import com.toomate.backend.categoria.domain.exception.CategoriaJaExisteException;
import com.toomate.backend.categoria.domain.exception.CategoriaNaoEncontradaException;
import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaUseCase {
    private final CategoriaGateway categoriaGateway;

    public CategoriaUseCase(CategoriaGateway categoriaGateway) {
        this.categoriaGateway = categoriaGateway;
    }

    public List<CategoriaDomain> listar() {
        return categoriaGateway.findAll();
    }

    public List<CategoriaDomain> listarPorNome(String nome) {
        String nomeNormalizado = validarNome(nome);
        return categoriaGateway.findByNomeContainingIgnoreCase(nomeNormalizado);
    }

    public CategoriaDomain buscarPorId(Integer id) {
        Integer idValidado = validarId(id);
        return categoriaGateway.findById(idValidado)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        String.format("Nao foi encontrada categoria com o id %d", idValidado)));
    }

    public CategoriaDomain cadastrar(String nome, Boolean rotatividade) {
        String nomeNormalizado = validarNome(nome);
        if (categoriaGateway.existsByNome(nomeNormalizado)) {
            throw new CategoriaJaExisteException("Ja existe uma categoria cadastrada com esse nome.");
        }

        CategoriaDomain categoria = new CategoriaDomain();
        categoria.setNome(nomeNormalizado);
        categoria.setRotatividade(rotatividade);

        return categoriaGateway.save(categoria);
    }

    public CategoriaDomain atualizar(Integer id, String nome, Boolean rotatividade) {
        Integer idValidado = validarId(id);
        String nomeNormalizado = validarNome(nome);

        CategoriaDomain existente = categoriaGateway.findById(idValidado)
                .orElseThrow(() -> new CategoriaNaoEncontradaException(
                        String.format("Nao foi encontrada categoria com o id %d", idValidado)));

        String nomeAtual = existente.getNome() == null ? "" : existente.getNome();
        if (!nomeAtual.equalsIgnoreCase(nomeNormalizado) && categoriaGateway.existsByNome(nomeNormalizado)) {
            throw new CategoriaJaExisteException("Ja existe uma categoria cadastrada com esse nome.");
        }

        existente.setNome(nomeNormalizado);
        existente.setRotatividade(rotatividade);
        return categoriaGateway.save(existente);
    }

    public void deletar(Integer id) {
        Integer idValidado = validarId(id);
        if (!categoriaGateway.existsById(idValidado)) {
            throw new CategoriaNaoEncontradaException(
                    String.format("Nao foi encontrada categoria com o id %d", idValidado));
        }
        categoriaGateway.deleteById(idValidado);
    }

    private String validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new CategoriaInvalidaException("O nome da categoria nao pode ser vazio.");
        }
        return nome.trim();
    }

    private Integer validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new CategoriaInvalidaException("O id da categoria deve ser maior que zero.");
        }
        return id;
    }
}
