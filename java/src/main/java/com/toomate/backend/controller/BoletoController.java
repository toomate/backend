package com.toomate.backend.controller;

import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.repository.BoletoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    private final BoletoRepository boletoRepository;

    public BoletoController(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Boleto>> listar() {

        List<Boleto> boletos = boletoRepository.findAll();

        if (boletos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(201).body(boletos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleto> buscarPorId(@PathVariable Integer idBoleto) {

        Optional<Boleto> boletoOpt = boletoRepository.findById(idBoleto);

        if (boletoOpt.isPresent()) {
            Boleto boleto = boletoOpt.get();
            return ResponseEntity.status(200).body(boleto);
        }

        return ResponseEntity.status(404).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleto> editar(@PathVariable Integer idBoleto, @RequestBody Boleto boleto) {

        boleto.setIdBoleto(idBoleto);

        if (boletoRepository.existsById(idBoleto)) {
            Boleto save = boletoRepository.save(boleto);
            return ResponseEntity.status(200).body(save);
        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Integer idBoleto) {

        if (boletoRepository.existsById(idBoleto)) {

            boletoRepository.deleteById(idBoleto);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();

    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Boleto>> buscarPorCategoria(@RequestParam String categoria) {

        List<Boleto> boletosEncontrados = boletoRepository.findByCategoriaContainingIgnoreCase(categoria);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);

    }

    @GetMapping("/fornecedores/{idFornecedor}")
    public ResponseEntity<List<Boleto>> buscarPorFornecedor(@PathVariable Integer idFornecedor) {

        List<Boleto> boletosEncontrados = boletoRepository.findByIdFornecedor(idFornecedor);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);

    }

}
