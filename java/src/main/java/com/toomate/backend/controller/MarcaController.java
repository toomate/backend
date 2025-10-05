package com.toomate.backend.controller;

import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaRequestDto;
import com.toomate.backend.dto.marca.MarcaResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.model.Marca;
import com.toomate.backend.repository.MarcaRepository;
import com.toomate.backend.service.FornecedorService;
import com.toomate.backend.service.InsumoService;
import com.toomate.backend.service.MarcaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas")
public class MarcaController {
    private final MarcaService marcaService;
    private final FornecedorService fornecedorService;
    private final InsumoService insumoService;

    public MarcaController(MarcaService marcaService, FornecedorService fornecedorService, InsumoService insumoService) {
        this.marcaService = marcaService;
        this.fornecedorService = fornecedorService;
        this.insumoService = insumoService;
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponseDto>> listar(){
        List<MarcaResponseDto> marcas = MarcaMapperDto.toDto(marcaService.listar());

        if(marcas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(marcas);

    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Marca>> filtroNome(@RequestParam String nome) {
        List<Marca> marca = marcaService.listarPorNome(nome);
        if (marca.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(marca);
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDto> cadastrar(@Valid @RequestBody MarcaRequestDto marca){
        if (marcaService.existePorNome(marca.getNome())){
            return ResponseEntity.status(409).build();
        }

        Insumo insumo = insumoService.insumoPorId(marca.getFkInsumo());
        Fornecedor fornecedor = fornecedorService.fornecedorPorId(marca.getFkFornecedor());
        Marca corpo = marcaService.cadastrar(marca, fornecedor, insumo);

        MarcaResponseDto responseDto = MarcaMapperDto.toDto(corpo);
        return ResponseEntity.status(201).body(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        marcaService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody MarcaRequestDto marca){

        if (marcaService.existePorId(id)) {
            Fornecedor fornecedor = fornecedorService.fornecedorPorId(marca.getFkFornecedor());
            Insumo insumo = insumoService.insumoPorId(marca.getFkInsumo());
            Marca marcaAtualizada = MarcaMapperDto.toEntity(marca, insumo, fornecedor);
            marcaService.atualizar(id, marcaAtualizada);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
