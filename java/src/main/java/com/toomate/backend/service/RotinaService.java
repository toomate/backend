package com.toomate.backend.service;

import com.toomate.backend.dto.rotina.RotinaInsumoRequest;
import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.rotina.RotinaMapper;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.RotinaInsumoRepository;
import com.toomate.backend.repository.RotinaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RotinaService {
    private final RotinaRepository rotinaRepository;
    private final InsumoService insumoService;
    private final RotinaInsumoRepository rotinaInsumoRepository;
    private final LoteService loteService;
    private final MarcaService marcaService;

    public RotinaService(RotinaRepository rotinaRepository, InsumoService insumoService, RotinaInsumoRepository rotinaInsumoRepository, LoteService loteService, MarcaService marcaService) {
        this.rotinaRepository = rotinaRepository;
        this.insumoService = insumoService;
        this.rotinaInsumoRepository = rotinaInsumoRepository;
        this.loteService = loteService;
        this.marcaService = marcaService;
    }

    public List<Rotina> listar() {
        return rotinaRepository.findAll();
    }

    public Rotina buscarPorId(Integer id) {
        return rotinaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id)));
    }

    public Rotina cadastrar(RotinaRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("A rotina não pode ser nula!");
        }
        Rotina rotina = RotinaMapper.toEntity(request);
        rotinaRepository.save(rotina);
        return rotina;
    }

    public RotinaInsumo associarInsumo(RotinaInsumoRequest request) {
        Insumo insumo = insumoService.insumoPorId(request.getFkInsumo());
        Rotina rotina = rotinaRepository.findById(request.getFkRotina()).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com o id %d", request.getFkRotina())));

        RotinaInsumo rotinaInsumo = new RotinaInsumo();
        rotinaInsumo.setRotina(rotina);
        rotinaInsumo.setInsumo(insumo);
        rotinaInsumo.setQuantidadeMedida(request.getQuantidadeMedida());

        return rotinaInsumoRepository.save(rotinaInsumo);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!rotinaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));
        }

        rotinaInsumoRepository.deleteByRotinaId(id);
        rotinaRepository.deleteById(id);
    }

    @Transactional
    public void darBaixa(Integer id) {
        if (!rotinaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));

        }
        List<RotinaInsumo> relacoes = rotinaInsumoRepository.findAllByRotinaId(id);

        for (RotinaInsumo relacao : relacoes) {
            Double qtdNecessaria = relacao.getQuantidadeMedida();

            List<Lote> lotesDisponiveis = loteService.lotePorInsumoId(relacao.getInsumo().getIdInsumo());

            for (Lote lote : lotesDisponiveis) {
                if (qtdNecessaria <= 0) break;

                if (lote.getQuantidadeMedida() >= qtdNecessaria) {
                    loteService.removerQuantidade(lote.getIdLote(), qtdNecessaria);
                    qtdNecessaria = 0.0;
                } else {
                    qtdNecessaria -= lote.getQuantidadeMedida();
                    loteService.removerQuantidade(lote.getIdLote(), lote.getQuantidadeMedida());
                }
            }

            if (qtdNecessaria > 0) {
                throw new EntradaInvalidaException("Faltou estoque para: " + relacao.getInsumo().getNome());
            }
        }


    }

    public Rotina atualizar(RotinaRequestDto rotina, Integer id) {
        Optional<Rotina> rotinaExiste = rotinaRepository.findById(id);
        if (rotina == null) {
            throw new EntradaInvalidaException("A rotina não pode ser nula!");
        }
        if (rotinaExiste.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));
        }

        Rotina rotinaParaAtualizar = RotinaMapper.toEntity(rotina);
        return rotinaRepository.save(rotinaParaAtualizar);
    }


}
