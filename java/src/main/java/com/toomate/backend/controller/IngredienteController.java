package com.toomate.backend.controller;

import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Ingrediente;
import com.toomate.backend.repository.IngredienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {
    private final IngredienteRepository repository;

    public IngredienteController(IngredienteRepository repository){this.repository=repository;}

    @GetMapping
    public ResponseEntity<List<Ingrediente>> listar(){
        List<Ingrediente> ingredientes = repository.findAll();
        if(ingredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Ingrediente>> filtroNome(@RequestParam String nome){
        List<Ingrediente> ingredientes = repository.findByNomeContainingIgnoreCase(nome);
        if(ingredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(ingredientes);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Ingrediente ingrediente){
            Optional<Ingrediente> ingredienteCheck = repository.findByNomeAndFkCategoria(ingrediente.getNome(), ingrediente.getFkCategoria());
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
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Ingrediente ingrediente){
        ingrediente.setIdIngrediente(id);
        if(repository.existsById(id)){
            repository.save(ingrediente);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
