package com.toomate.backend.controller;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.repository.FornecedorRepository;
import com.toomate.backend.service.FornecedorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        List<Fornecedor> fornecedores = fornecedorService.listar();

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> retornarPeloId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(fornecedorService.retornarPeloId(id));
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<Fornecedor>> filtrar(@RequestParam String razaoSocial) {
        List<Fornecedor> fornecedores = fornecedorService.filtrar(razaoSocial);

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @PostMapping
    public ResponseEntity<Fornecedor> cadastrar(@RequestBody @Valid FornecedorRequestDto fornecedor) {
        return ResponseEntity.status(201).body(fornecedorService.cadastrar(fornecedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.status(200).body(fornecedorService.atualizar(id, fornecedor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
