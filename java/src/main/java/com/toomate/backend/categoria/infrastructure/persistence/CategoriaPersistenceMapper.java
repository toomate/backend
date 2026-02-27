package com.toomate.backend.categoria.infrastructure.persistence;

import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import com.toomate.backend.model.Categoria;

public final class CategoriaPersistenceMapper {
    private CategoriaPersistenceMapper() {
    }

    public static CategoriaDomain toDomain(Categoria entity) {
        if (entity == null) {
            return null;
        }

        CategoriaDomain categoria = new CategoriaDomain();
        categoria.setId(entity.getIdCategoria());
        categoria.setNome(entity.getNome());
        categoria.setRotatividade(entity.getRotatividade());
        return categoria;
    }

    public static Categoria toEntity(CategoriaDomain domain) {
        if (domain == null) {
            return null;
        }

        Categoria categoria = new Categoria();
        categoria.setIdCategoria(domain.getId());
        categoria.setNome(domain.getNome());
        categoria.setRotatividade(domain.getRotatividade());
        return categoria;
    }
}
