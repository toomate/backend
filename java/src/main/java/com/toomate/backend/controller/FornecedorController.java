package com.toomate.backend.controller;

import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.repository.FornecedorRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
    private final FornecedorRepository fornecedorRepository;

    public FornecedorController(FornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        List<Fornecedor> fornecedores = fornecedorRepository.findAll();

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> retornarPeloId(@PathVariable Integer id){
        return ResponseEntity.of(fornecedorRepository.findById(id));
    }

    @GetMapping("/pesquisa")
    public ResponseEntity<List<Fornecedor>> filtrar(@RequestParam String razaoSocial) {
        List<Fornecedor> fornecedores = fornecedorRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial);

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Fornecedor fornecedor) {
        if (fornecedor == null) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.status(201).body((String.format("/fornecedores/%d", fornecedorRepository.save(fornecedor).getId())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        if(fornecedor.getId() != null && !fornecedor.getId().equals(id)){
            return ResponseEntity.status(400).build();
        }

        Optional<Fornecedor> fornecedorParaAtualizar = fornecedorRepository.findById(id);

        if (fornecedorParaAtualizar.isPresent()) {
            fornecedor.setId(id);
            return ResponseEntity.status(200).body(fornecedorRepository.save(fornecedor));
        }

        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (fornecedorRepository.existsById(id)) {
            fornecedorRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }
}
