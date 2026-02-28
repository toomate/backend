package com.toomate.backend.categoria.infrastructure.persistence;

import com.toomate.backend.categoria.application.port.out.CategoriaGateway;
import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import com.toomate.backend.repository.CategoriaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoriaJpaGateway implements CategoriaGateway {
    private final CategoriaRepository categoriaRepository;

    public CategoriaJpaGateway(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public List<CategoriaDomain> findAll() {
        return categoriaRepository.findAll()
                .stream()
                .map(CategoriaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<CategoriaDomain> findByNomeContainingIgnoreCase(String nome) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(CategoriaPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<CategoriaDomain> findById(Integer id) {
        return categoriaRepository.findById(id)
                .map(CategoriaPersistenceMapper::toDomain);
    }

    @Override
    public CategoriaDomain save(CategoriaDomain categoria) {
        return CategoriaPersistenceMapper.toDomain(
                categoriaRepository.save(CategoriaPersistenceMapper.toEntity(categoria))
        );
    }

    @Override
    public boolean existsById(Integer id) {
        return categoriaRepository.existsById(id);
    }

    @Override
    public boolean existsByNomeIgnoreCase(String nome) {
        return categoriaRepository.existsByNomeIgnoreCase(nome);
    }

    @Override
    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
    }
}
