package com.toomate.backend.service;

import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.repository.ArquivoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArquivoService {
    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public List<Arquivo> listar(){
        return arquivoRepository.findAll();
    }

    public Arquivo buscarPorId(Integer id) {
        return arquivoRepository.findById(id)
                .orElseThrow(()-> new EntidadeNaoEncontradaException(
                        String.format("O arquivo com o id %d não foi encontrado!", id)));
    }

    public Arquivo cadastrarArquivo(Arquivo arquivo) {
        if (arquivo == null) {
            throw new EntradaInvalidaException("O arquivo é inválido");
        }
        return arquivoRepository.save(arquivo);
    }

    public void deletarArquivo (Integer id) {
        if (!arquivoRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado um arquivo com o id %d", id));
        }
        arquivoRepository.deleteById(id);
    }

}
