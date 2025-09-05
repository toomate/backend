package com.toomate.backend.controller;

import com.toomate.backend.model.Categoria;
import com.toomate.backend.repository.CategoriaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository){this.repository = repository;}

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){
        List<Categoria> categorias = repository.findAll();
        if(categorias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categorias);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Categoria>> filtroNome(@RequestParam String nome){
        List<Categoria> categorias = repository.findByNomeContainingIgnoreCase(nome);
        if(categorias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categorias);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Categoria categoria){
        if(repository.existsByNome(categoria.getNome())){
            return ResponseEntity.status(409).build();
        }
        repository.save(categoria);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletar(@PathVariable String nome){
        if(repository.existsByNome(nome)){
            repository.deleteById(repository.findByNome(nome).getIdCategoria());
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Categoria categoria){
        categoria.setIdCategoria(id);
        if(repository.existsById(id)){
            repository.save(categoria);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
