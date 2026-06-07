package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Lote;
import com.toomate.backend.repository.InsumoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InsumoService {
    private final InsumoRepository insumoRepository;
    private final AuditService auditService;

    public InsumoService(InsumoRepository insumoRepository, AuditService auditService) {
        this.insumoRepository = insumoRepository;
        this.auditService = auditService;
    }

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public List<Insumo> listarPorNome(String nome) {
        return insumoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", key = "'todos'"),
            @CacheEvict(cacheNames = "lotes", key = "'todos'"),
            @CacheEvict(cacheNames = "vencimentos", key = "'todos'")
    })
    public Insumo cadastrar(Insumo insumo) {
        String usuarioLogado = getUsuarioLogado();
        if (insumo == null) {
            throw new EntradaInvalidaException("O insumo nao pode ser nulo!");
        }

        log.info("Usuário {} cadastrou o insumo: {} às {}", usuarioLogado, insumo.getNome(), LocalDateTime.now());
        auditService.registrar(usuarioLogado, "CADASTRO", "INSUMO", "Cadastrou insumo: " + insumo.getNome());
        return insumoRepository.save(insumo);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", key = "'todos'"),
            @CacheEvict(cacheNames = "lotes", key = "'todos'"),
            @CacheEvict(cacheNames = "vencimentos", key = "'todos'")
    })
    public void deletar(Integer id) {
        String usuarioLogado = getUsuarioLogado();
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }

        insumoRepository.deleteById(id);
        log.info("Usuário {} deletou o insumo com ID: {} às {}", usuarioLogado, id, LocalDateTime.now());
        auditService.registrar(usuarioLogado, "DELECAO", "INSUMO", "Deletou insumo ID: " + id);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", key = "'todos'"),
            @CacheEvict(cacheNames = "lotes", key = "'todos'"),
            @CacheEvict(cacheNames = "vencimentos", key = "'todos'")
    })
    public Insumo atualizar(Integer id, Insumo insumo) {
        String usuarioLogado = getUsuarioLogado();
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }
        insumo.setIdInsumo(id);
        log.info("Usuário {} atualizou o insumo com ID: {} às {}", usuarioLogado, id, LocalDateTime.now());
        auditService.registrar(usuarioLogado, "ATUALIZACAO", "INSUMO", "Atualizou insumo ID: " + id);
        return insumoRepository.save(insumo);
    }


    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    public Insumo excluirInsumo(Integer idInsumo) {
        Insumo insumo = insumoRepository.findById(idInsumo).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrado um insumo com o id " + idInsumo));

        insumo.setAtivo(false);

        return insumoRepository.save(insumo);
    }

    public Boolean existePorNome(String nome) {
        return insumoRepository.existsByNome(nome);
    }

    public Boolean existePorId(Integer id) {
        return insumoRepository.existsById(id);
    }

    public Insumo insumoPorId(Integer id) {
        return insumoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado insumo com o id %d", id))
        );
    }

    public List<Insumo> insumosPorId(List<Integer> ids) {
        return insumoRepository.findAllById(ids);
    }

    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

//    public List<String> listarUnidadesDeMedida() {
//        return insumoRepository.listarUnidadesDeMedida();
//    }
}
