package com.toomate.backend.service;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (insumo == null) {
            throw new EntradaInvalidaException("O insumo nao pode ser nulo!");
        }

        return insumoRepository.save(insumo);
    }

    public void deletar(Integer id) {
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }

        insumoRepository.deleteById(id);
    }

    public Insumo atualizar(Integer id, Insumo insumo) {
        if (!insumoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado nenhum insumo com o id %d", id));
        }
        insumo.setIdInsumo(id);
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
}
