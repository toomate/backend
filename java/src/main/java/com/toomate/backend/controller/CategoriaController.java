package com.toomate.backend.controller;

import com.toomate.backend.dto.categoria.CategoriaMapperDto;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "Bearer")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){this.categoriaService = categoriaService;}


    @Operation(summary = "Listar categorias",
            description = "Retorna lista de categorias (codigo 200) ou codigo 204 se não houver categorias",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar(){
        List<CategoriaResponseDto> dtos = CategoriaMapperDto.toResponseDto(categoriaService.listar());

        if (dtos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(dtos);
    }

    @Operation(summary = "Filtrar categorias por nome",
            description = "Retorna lista de categorias filtradas por nome (codigo 200) ou codigo 204 se não houver categorias",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/por-nome")
    public ResponseEntity<List<Categoria>> filtroNome(@RequestParam String nome){
        List<Categoria> categorias = categoriaService.listarPorNome(nome);
        if(categorias.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(categorias);
    }

    @Operation(summary = "Cadastrar categoria",
            description = "Retorna a categoria cadastrada (codigo 201) ou codigo 409 se a categoria já existir",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @PostMapping
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody CategoriaRequestDto categoria){
        if(categoriaService.existePorNome(categoria.getNome())){
            return ResponseEntity.status(409).build();
        }
        Categoria corpo = categoriaService.cadastrar(categoria);
        return ResponseEntity.status(201).body(corpo);
    }

    @Operation(summary = "Deletar categoria",
            description = "Deleta categoria por id e retorna o codigo 204",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        categoriaService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar categoria",
            description = "Retorna categoria atualizada (codigo 204) ou codigo 404 se não encontar a categoria pelo id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria editada"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
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
