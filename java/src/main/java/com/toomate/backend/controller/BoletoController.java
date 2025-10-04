package com.toomate.backend.controller;

import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.repository.BoletoRepository;
import com.toomate.backend.service.BoletoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @PostMapping
    public ResponseEntity<Boleto> cadastrar(@RequestBody BoletoRequestDto request) {
        return ResponseEntity.status(201).body(boletoService.cadastrar(request));
    }

    @GetMapping
    public ResponseEntity<List<Boleto>> listar() {

        List<Boleto> boletos = boletoService.listarBoletos();

        if (boletos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(201).body(boletos);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Boleto> buscarPorId(@PathVariable Integer idBoleto) {
        return ResponseEntity.status(200).body(boletoService.buscarPorId(idBoleto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boleto> editar(@PathVariable Integer idBoleto, @RequestBody Boleto boleto) {

        return ResponseEntity.status(200).body(boletoService.editar(idBoleto, boleto));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Integer idBoleto) {
        boletoService.deletarPorId(idBoleto);
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/por-categoria")
    public ResponseEntity<List<Boleto>> buscarPorCategoria(@RequestParam String categoria) {

        List<Boleto> boletosEncontrados = boletoService.buscarPorCategoria(categoria);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);

    }

    @GetMapping("/fornecedores/{idFornecedor}")
    public ResponseEntity<List<Boleto>> buscarPorFornecedor(@PathVariable Integer idFornecedor) {

        List<Boleto> boletosEncontrados = boletoService.buscarPorFornecedor(idFornecedor);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);
    }

}
