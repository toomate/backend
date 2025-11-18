package com.toomate.backend.service;

import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.mapper.boleto.BoletoMapper;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.repository.BoletoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    public Boleto cadastrar(BoletoRequestDto request) {
        Boleto boleto = BoletoMapper.toEntity(request);
        return boletoRepository.save(boleto);
    }

    public List<Boleto> listarBoletos() {
        return boletoRepository.findAll();
    }

    public Boleto buscarPorId(Integer id) {

        Boleto boleto = boletoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("O boleto não foi encontrado!"));
        return boleto;
    }

    public Boleto editar(Integer idBoleto, Boleto boleto) {

        boleto.setIdBoleto(idBoleto);

        if (boletoRepository.existsById(idBoleto)) {
            Boleto save = boletoRepository.save(boleto);

            return save;
        }

        throw new EntidadeNaoEncontradaException("O boleto não foi encontrado!");
    }

    public void deletarPorId(Integer idBoleto) {

        if (!boletoRepository.existsById(idBoleto)) {
            throw new EntidadeNaoEncontradaException("O boleto não foi encontrado!");
        }
        boletoRepository.deleteById(idBoleto);
    }

    public List<Boleto> buscarPorCategoria(String categoria) {

        List<Boleto> boletosEncontrados = boletoRepository.findByCategoriaContainingIgnoreCase(categoria);
            return boletosEncontrados;
    }

    public List<Boleto> buscarPorFornecedor(Integer idFornecedor) {

        List<Boleto> boletosEncontrados = boletoRepository.findByIdFornecedor(idFornecedor);
            return boletosEncontrados;

    }

    public Boolean existePorId(Integer id){
        return boletoRepository.existsById(id);
    }


}
