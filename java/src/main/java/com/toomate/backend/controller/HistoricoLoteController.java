package com.toomate.backend.controller;

import com.toomate.backend.dto.historico_lote.HistoricoLoteResponseDto;
import com.toomate.backend.mapper.historico_lote.HistoricoLoteMapper;
import com.toomate.backend.model.HistoricoLote;
import com.toomate.backend.service.HistoricoLoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/historico-lote")
public class HistoricoLoteController {
    private final HistoricoLoteService service;

    public HistoricoLoteController(HistoricoLoteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HistoricoLoteResponseDto>> listar() {
        List<HistoricoLote> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        List<HistoricoLoteResponseDto> response = HistoricoLoteMapper.toResponse(lista);
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoricoLoteResponseDto> buscarPorId(@PathVariable Integer id) {
        HistoricoLoteResponseDto response = HistoricoLoteMapper.toResponse(service.buscarPorId(id));
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<HistoricoLoteResponseDto> cadastrar(@RequestBody HistoricoLote request) {
        HistoricoLoteResponseDto response = HistoricoLoteMapper.toResponse(service.cadastrar(request));
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistoricoLoteResponseDto> atualizar(@PathVariable Integer id, @RequestBody HistoricoLote request) {
        HistoricoLoteResponseDto response = HistoricoLoteMapper.toResponse(service.atualizar(id, request));
        return ResponseEntity.status(201).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

}
