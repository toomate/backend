package com.toomate.backend.controller;

import com.toomate.backend.mapper.categoria.CategoriaMapper;
import com.toomate.backend.dto.categoria.CategoriaRequestDto;
import com.toomate.backend.dto.categoria.CategoriaResponseDto;
import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.mapper.fornecedor.FornecedorMapper;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.service.CategoriaService;
import com.toomate.backend.service.MarcaService;
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

    private final CategoriaService categoriaService;
    private final MarcaService marcaService;

    public CategoriaController(CategoriaService categoriaService, MarcaService marcaService) {
        this.categoriaService = categoriaService;
        this.marcaService = marcaService;
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
        List<CategoriaResponseDto> dtos = CategoriaMapper.toResponseDto(categoriaService.listar());

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
        List<CategoriaResponseDto> dtos = CategoriaMapper.toResponseDto(categoriaService.listarPorNome(nome));

        if (dtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Listar fornecedores por categoria",
            description = "Retorna os fornecedores relacionados a categoria via marcas e insumos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fornecedores",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteudo"),
                    @ApiResponse(responseCode = "404", description = "Categoria nao encontrada")
            })
    @GetMapping("/{id}/fornecedores")
    public ResponseEntity<List<FornecedorResponseDto>> listarFornecedoresPorCategoria(@PathVariable Integer id) {
        categoriaService.categoriaPorId(id);
        List<FornecedorResponseDto> fornecedores = FornecedorMapper.toResponseList(
                marcaService.listarFornecedoresPorCategoria(id)
        );

        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fornecedores);
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
        Categoria criada = categoriaService.cadastrar(categoria);
        return ResponseEntity.status(201).body(CategoriaMapper.toDto(criada));
    }

    @Operation(summary = "Deletar categoria",
            description = "Deleta categoria por id e retorna o codigo 204",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        categoriaService.deletar(id);
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
        categoriaService.atualizar(id, categoria);
        return ResponseEntity.noContent().build();
    }
}
