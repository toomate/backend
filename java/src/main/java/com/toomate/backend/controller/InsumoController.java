package com.toomate.backend.controller;

import com.toomate.backend.model.Insumo;
import com.toomate.backend.repository.InsumoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
public class InsumoController {
    private final InsumoRepository repository;

    public InsumoController(InsumoRepository repository){this.repository=repository;}

    @GetMapping
    public ResponseEntity<List<Insumo>> listar(){
        List<Insumo> ingredientes = repository.findAll();
        if(ingredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Insumo>> filtroNome(@RequestParam String nome){
        List<Insumo> ingredientes = repository.findByNomeContainingIgnoreCase(nome);
        if(ingredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Insumo ingrediente){
            Optional<Insumo> ingredienteCheck = repository.findByNomeAndFkCategoria(ingrediente.getNome(), ingrediente.getFkCategoria());
        if(ingredienteCheck.isPresent()){
            return ResponseEntity.status(409).build();
        }
        repository.save(ingrediente);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome){
        if(repository.existsByNome(nome)){
            repository.deleteById(repository.findByNome(nome).getIdIngrediente());
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Insumo ingrediente){
        ingrediente.setIdIngrediente(id);
        if(repository.existsById(id)){
            repository.save(ingrediente);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
