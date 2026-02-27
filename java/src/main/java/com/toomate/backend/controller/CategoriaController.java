package com.toomate.backend.controller;

import com.toomate.backend.categoria.application.usecase.CategoriaUseCase;
import com.toomate.backend.categoria.domain.model.CategoriaDomain;
import com.toomate.backend.categoria.interfaces.rest.CategoriaDtoMapper;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@SecurityRequirement(name = "Bearer")
public class CategoriaController {

    private final CategoriaUseCase categoriaUseCase;

    public CategoriaController(CategoriaUseCase categoriaUseCase) {
        this.categoriaUseCase = categoriaUseCase;
    }

    @Operation(summary = "Listar categorias",
            description = "Retorna lista de categorias (codigo 200) ou codigo 204 se nao houver categorias",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDto>> listar() {
        List<CategoriaResponseDto> dtos = categoriaUseCase.listar().stream()
                .map(CategoriaDtoMapper::toResponse)
                .toList();

        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Filtrar categorias por nome",
            description = "Retorna lista de categorias filtradas por nome (codigo 200) ou codigo 204 se nao houver categorias",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de categorias",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @GetMapping("/por-nome")
    public ResponseEntity<List<CategoriaResponseDto>> filtroNome(@RequestParam String nome) {
        List<CategoriaResponseDto> dtos = categoriaUseCase.listarPorNome(nome).stream()
                .map(CategoriaDtoMapper::toResponse)
                .toList();

        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Cadastrar categoria",
            description = "Retorna a categoria cadastrada (codigo 201) ou codigo 409 se a categoria ja existir",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Categoria criada",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito")
            })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> cadastrar(@Valid @RequestBody CategoriaRequestDto categoria) {
        CategoriaDomain criada = categoriaUseCase.cadastrar(categoria.getNome(), categoria.getRotatividade());
        return ResponseEntity.status(201).body(CategoriaDtoMapper.toResponse(criada));
    }

    @Operation(summary = "Deletar categoria",
            description = "Deleta categoria por id e retorna o codigo 204",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar categoria",
            description = "Retorna codigo 204 apos atualizar a categoria",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria editada"),
                    @ApiResponse(responseCode = "404", description = "Nao encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaRequestDto categoria) {
        categoriaUseCase.atualizar(id, categoria.getNome(), categoria.getRotatividade());
        return ResponseEntity.noContent().build();
    }
}
