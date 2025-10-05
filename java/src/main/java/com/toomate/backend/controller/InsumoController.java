package com.toomate.backend.controller;

import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.service.CategoriaService;
import com.toomate.backend.service.InsumoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
public class InsumoController {
    private final InsumoService insumoService;
    private final CategoriaService categoriaService;

    public InsumoController(InsumoService insumoService, CategoriaService categoriaService) {
        this.insumoService = insumoService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<InsumoResponseDto>> listar(){
        List<InsumoResponseDto> insumos = InsumoMapperDto.toDto(insumoService.listar());

        if(insumos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(insumos);
    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Insumo>> filtroNome(@RequestParam String nome){
        List<Insumo> insumos = insumoService.listarPorNome(nome);
        if(insumos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(insumos);
    }

    @PostMapping
<<<<<<< HEAD
    public ResponseEntity<InsumoResponseDto> cadastrar(@Valid @RequestBody InsumoRequestDto insumo){
=======
    public ResponseEntity<Insumo> cadastrar(@Valid @RequestBody InsumoRequestDto insumo){
>>>>>>> 8444653ac17a36214bf362ff02434524a7b60c59
        if (insumoService.existePorNome(insumo.getNome())) {
            return ResponseEntity.status(409).build();
        }

        Categoria categoria = categoriaService.categoriaPorId(insumo.getFkCategoria());
        Insumo corpo = insumoService.cadastrar(insumo, categoria);

<<<<<<< HEAD
        InsumoResponseDto responseDto = InsumoMapperDto.toDto(corpo);
        return ResponseEntity.status(201).body(responseDto);
=======
        return ResponseEntity.status(201).body(corpo);
>>>>>>> 8444653ac17a36214bf362ff02434524a7b60c59
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        insumoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody InsumoRequestDto insumo){
        if(insumoService.existePorId(id)){
            Categoria categoria = categoriaService.categoriaPorId(insumo.getFkCategoria());
            Insumo insumoAtualizado = InsumoMapperDto.toEntity(insumo, categoria);
            insumoService.atualizar(id, insumoAtualizado);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
