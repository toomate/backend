package com.toomate.backend.service;

import com.toomate.backend.dto.arquivo_relacionamento.ArquivoRelacionamentoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.model.ArquivoRelacionamento;
import com.toomate.backend.repository.ArquivoRelacionamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class ArquivoRelacionamentoService {
    private final ArquivoRelacionamentoRepository arquivoRelacionamentoRepository;
    private final LoteService loteService;
    private final InsumoService insumoService;
    private final DividaService dividaService;
    private final BoletoService boletoService;

    public ArquivoRelacionamentoService(ArquivoRelacionamentoRepository arquivoRelacionamentoRepository, LoteService loteService, InsumoService insumoService, DividaService dividaService, BoletoService boletoService) {
        this.arquivoRelacionamentoRepository = arquivoRelacionamentoRepository;
        this.loteService = loteService;
        this.insumoService = insumoService;
        this.dividaService = dividaService;
        this.boletoService = boletoService;
    }

    public ArquivoRelacionamento relacionar(Arquivo arquivo, ArquivoRelacionamentoRequestDto relacionamento) {
        ArquivoRelacionamento arquivoRelacionamento = new ArquivoRelacionamento();
        arquivoRelacionamento.setArquivo(arquivo);
        arquivoRelacionamento.setIdEntidade(relacionamento.getIdEntidade());
        arquivoRelacionamento.setTipoEntidade(relacionamento.getTipoEntidade().getTipo());

        return arquivoRelacionamentoRepository.save(arquivoRelacionamento);
    }

    public void deletar(Arquivo arquivo) {
        ArquivoRelacionamento arquivoRelacionamento = arquivoRelacionamentoRepository.findByArquivo(arquivo).orElseThrow(() -> new EntidadeNaoEncontradaException("Não foi encontrado um relacionamento com este arquivo"));

        arquivoRelacionamentoRepository.delete(arquivoRelacionamento);
    }

    public void validarEntidade(ArquivoRelacionamentoRequestDto relacionamento) {
        Boolean existe = false;
        if (relacionamento.getTipoEntidade() == null) {
            throw new EntradaInvalidaException("O tipo de entidade não pode ser nulo!");
        }
        switch (relacionamento.getTipoEntidade()) {
            case LOTE -> {
                existe = loteService.existePorId(relacionamento.getIdEntidade());
            }
            case BOLETO -> {
                existe = boletoService.existePorId(relacionamento.getIdEntidade());
            }
            case DIVIDA -> {
                existe = dividaService.existePorId(relacionamento.getIdEntidade());
            }
            case INSUMO -> {
                existe = insumoService.existePorId(relacionamento.getIdEntidade());
            }
        }

        if (!existe) {
            throw new EntradaInvalidaException(String.format("A entidade %s com o ID %d não foi encontrada!", relacionamento.getTipoEntidade(), relacionamento.getIdEntidade()));
        }
    }
}
