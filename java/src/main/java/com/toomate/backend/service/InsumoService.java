package com.toomate.backend.service;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class InsumoService {
    private final InsumoRepository insumoRepository;

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public List<Insumo> listarPorNome(String nome) {
        return insumoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Insumo cadastrar(Insumo insumo) {
        String usuarioLogado = getUsuarioLogado();
        if (insumo == null) {
            throw new EntradaInvalidaException("O insumo nao pode ser nulo!");
        }
        log.info("Usuário {} cadastrou o insumo: {} às {}", usuarioLogado, insumo.getNome(), LocalDateTime.now());
        return insumoRepository.save(insumo);
    }

    public void deletar(Integer id) {
        String usuarioLogado = getUsuarioLogado();
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }

        insumoRepository.deleteById(id);
        log.info("Usuário {} deletou o insumo com ID: {} às {}", usuarioLogado, id, LocalDateTime.now());
    }

    public Insumo atualizar(Integer id, Insumo insumo) {
        String usuarioLogado = getUsuarioLogado();
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }
        insumo.setIdInsumo(id);
        log.info("Usuário {} atualizou o insumo com ID: {} às {}", usuarioLogado, id, LocalDateTime.now());
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

    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
