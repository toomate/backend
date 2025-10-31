package com.toomate.backend.service;

import com.toomate.backend.dto.rotina.DiaSemanaRequestDto;
import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.rotina.DiaRotinaMapper;
import com.toomate.backend.mapper.rotina.RotinaMapper;
import com.toomate.backend.model.DiaRotina;
import com.toomate.backend.model.DiaSemana;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Rotina;
import com.toomate.backend.repository.DiaRotinaRepository;
import com.toomate.backend.repository.RotinaRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RotinaService {
    private final RotinaRepository rotinaRepository;
    private final InsumoService insumoService;
    private final DiaSemanaService diaSemanaService;
    private final DiaRotinaRepository diaRotinaRepository;

    public RotinaService(RotinaRepository rotinaRepository, InsumoService insumoService, DiaSemanaService diaSemanaService, DiaRotinaRepository diaRotinaRepository) {
        this.rotinaRepository = rotinaRepository;
        this.insumoService = insumoService;
        this.diaSemanaService = diaSemanaService;
        this.diaRotinaRepository = diaRotinaRepository;
    }

    public List<Rotina> listar() {
        return rotinaRepository.findAll();
    }

    public Rotina buscarPorId(Integer id) {
        return rotinaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id)));
    }

    @Transactional
    public Rotina cadastrar(RotinaRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("A rotina não pode ser nula!");
        }
        Insumo insumo = insumoService.insumoPorId(request.getIdInsumo());
        Rotina rotina = RotinaMapper.toEntity(request, insumo);
        rotinaRepository.save(rotina);

        DiaSemana diaSemana = diaSemanaService.buscarPeloDia(request.getDiaSemana().getDesc());

        DiaRotina diaRotina = DiaRotinaMapper.toEntity(rotina, diaSemana);
        diaRotinaRepository.save(diaRotina);

        return rotina;
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

        Rotina rotinaParaAtualizar = RotinaMapper.toEntity(rotina, rotinaExiste.get().getInsumo());
        return rotinaRepository.save(rotinaParaAtualizar);
    }



}
