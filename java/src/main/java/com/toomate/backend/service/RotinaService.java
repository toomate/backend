package com.toomate.backend.service;

import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.rotina.RotinaMapper;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Rotina;
import com.toomate.backend.model.RotinaInsumo;
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

    public RotinaService(RotinaRepository rotinaRepository, InsumoService insumoService, RotinaInsumoRepository rotinaInsumoRepository) {
        this.rotinaRepository = rotinaRepository;
        this.insumoService = insumoService;
        this.rotinaInsumoRepository = rotinaInsumoRepository;
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

    public RotinaInsumo associarInsumo(Integer idInsumo, Integer idRotina){
        Insumo insumo = insumoService.insumoPorId(idInsumo);
        Rotina rotina = rotinaRepository.findById(idRotina).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com o id %d", idRotina)));

        RotinaInsumo rotinaInsumo = new RotinaInsumo();
        rotinaInsumo.setRotina(rotina);
        rotinaInsumo.setInsumo(insumo);

        return rotinaInsumoRepository.save(rotinaInsumo);
    }

    public void deletar(Integer id) {
        if (!rotinaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));
        }

        rotinaRepository.deleteById(id);
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
