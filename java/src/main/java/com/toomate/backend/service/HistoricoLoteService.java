package com.toomate.backend.service;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.model.HistoricoLote;
import com.toomate.backend.repository.HistoricoLoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoricoLoteService {
    private final HistoricoLoteRepository repository;

    public HistoricoLoteService(HistoricoLoteRepository repository) {
        this.repository = repository;
    }

    public HistoricoLote cadastrar(HistoricoLote request) {
        return repository.save(request);
    }

    public List<HistoricoLote> listar() {
        return repository.findAll();
    }

    public HistoricoLote buscarPorId(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrado um histórico com o Id " + id));
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Não foi encontrado um histórico com o Id " + id);
        }

        repository.deleteById(id);
    }

    public HistoricoLote atualizar(Integer id, HistoricoLote lote){
        if (!repository.existsById(id)) {
            throw new EntidadeNaoEncontradaException("Não foi encontrado um histórico com o Id " + id);
        }

        lote.setIdHistorico(id);

        return repository.save(lote);
    }

}
