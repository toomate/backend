package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import com.toomate.backend.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InsumoService {
    private final InsumoRepository insumoRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditService auditService;

    public InsumoService(InsumoRepository insumoRepository, UsuarioRepository usuarioRepository, AuditService auditService) {
        this.insumoRepository = insumoRepository;
        this.usuarioRepository = usuarioRepository;
        this.auditService = auditService;
    }

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public List<Insumo> listarAtivo() {
        return insumoRepository.findAllByAtivoTrue();
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
        Insumo salvo = insumoRepository.save(insumo);
        log.info("Usuário '{}' cadastrou insumo ID {} | Nome: {} | Qtd mínima: {}",
                usuarioLogado, salvo.getIdInsumo(), salvo.getNome(), salvo.getQtdMinima());
        auditService.registrar(usuarioLogado, "CADASTRO", "INSUMO", salvo.getIdInsumo(),
                String.format("Cadastrou insumo '%s' com qtd mínima: %d", salvo.getNome(), salvo.getQtdMinima()),
                null,
                Map.of("nome", salvo.getNome(), "qtdMinima", salvo.getQtdMinima()));
        return salvo;
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

        Insumo insumo = insumoRepository.findById(id).orElse(null);
        insumoRepository.deleteById(id);
        log.info("Usuário '{}' deletou insumo ID {} | Nome: {}",
                usuarioLogado, id, insumo != null ? insumo.getNome() : "N/A");
        auditService.registrar(usuarioLogado, "DELECAO", "INSUMO", id,
                String.format("Deletou insumo '%s'", insumo != null ? insumo.getNome() : "ID " + id),
                insumo != null ? Map.of("nome", insumo.getNome(), "qtdMinima", insumo.getQtdMinima()) : null,
                null);
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
        Insumo anterior = insumoRepository.findById(id).orElse(null);
        insumo.setIdInsumo(id);
        Insumo atualizado = insumoRepository.save(insumo);
        log.info("Usuário '{}' atualizou insumo ID {} | Nome: {} → {}",
                usuarioLogado, id,
                anterior != null ? anterior.getNome() : "N/A", atualizado.getNome());
        auditService.registrar(usuarioLogado, "ATUALIZACAO", "INSUMO", id,
                String.format("Atualizou insumo '%s'", atualizado.getNome()),
                anterior != null ? Map.of("nome", anterior.getNome(), "qtdMinima", anterior.getQtdMinima()) : null,
                Map.of("nome", atualizado.getNome(), "qtdMinima", atualizado.getQtdMinima()));
        return atualizado;
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
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }
}
