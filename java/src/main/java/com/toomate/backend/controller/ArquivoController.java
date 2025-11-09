package com.toomate.backend.controller;

import com.toomate.backend.model.Arquivo;
import com.toomate.backend.service.ArquivoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/arquivos")
@SecurityRequirement(name = "Bearer")
public class ArquivoController {
    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping
    public ResponseEntity<List<Arquivo>> listar() {
        List<Arquivo> arquivos = arquivoService.listar();

        if (arquivos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(arquivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arquivo> retornarPeloId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(arquivoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Arquivo> cadastrar(@RequestBody Arquivo arquivo) {
        return ResponseEntity.status(201).body(arquivoService.cadastrarArquivo(arquivo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        arquivoService.deletarArquivo(id);
        return ResponseEntity.status(204).build();
    }
}
