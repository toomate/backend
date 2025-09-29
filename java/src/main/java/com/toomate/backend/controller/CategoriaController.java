package com.toomate.backend.controller;

import com.toomate.backend.dto.categoria.CategoriaMapperDto;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.service.CategoriaService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){this.categoriaService = categoriaService;}

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar(){
        List<CategoriaResponseDto> dtos = CategoriaMapperDto.toResponseDto(categoriaService.listar());

        if (dtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(dtos);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Categoria>> filtroNome(@RequestParam String nome){
        List<Categoria> categorias = categoriaService.listarPorNome(nome);
        if(categorias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categorias);
    }

    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody CategoriaRequestDto categoria){
        if(categoriaService.existePorNome(categoria.getNome())){
            return ResponseEntity.status(409).build();
        }
        Categoria corpo = categoriaService.cadastrar(categoria);
        return ResponseEntity.status(201).body(corpo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        categoriaService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Integer id, @RequestBody Categoria categoria){
        categoria.setIdCategoria(id);
        if(categoriaService.existePorId(id)){
            categoriaService.atualizar(id, categoria);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
