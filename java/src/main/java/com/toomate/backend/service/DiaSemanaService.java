package com.toomate.backend.service;

import com.toomate.backend.dto.rotina.DiaSemanaRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.rotina.DIaSemanaMapper;
import com.toomate.backend.model.DiaSemana;
import com.toomate.backend.repository.DiaSemanaRepository;
import org.springframework.stereotype.Service;

@Service
public class DiaSemanaService {
    private final DiaSemanaRepository diaSemanaRepository;

    public DiaSemanaService(DiaSemanaRepository diaSemanaRepository) {
        this.diaSemanaRepository = diaSemanaRepository;
    }

    public DiaSemana cadastrar(DiaSemanaRequestDto request){
        if (request == null){
            throw new EntradaInvalidaException("O dia não pode ser nulo!!");
        }

        DiaSemana diaSemana = DIaSemanaMapper.toEntity(request);

        return diaSemanaRepository.save(diaSemana);
    }

    public DiaSemana buscarPeloDia(String dia){
        return diaSemanaRepository.findByNomeDia(dia).orElseThrow(() -> new EntidadeNaoEncontradaException("Dia da semana não encontrado!"));
    }
}
