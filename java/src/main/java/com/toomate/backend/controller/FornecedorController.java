package com.toomate.backend.controller;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.dto.fornecedor.FornecedorResponseDto;
import com.toomate.backend.mapper.fornecedor.FornecedorMapper;
import com.toomate.backend.service.FornecedorService;
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
@RequestMapping("/fornecedores")
@SecurityRequirement(name = "Bearer")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @Operation(summary = "Listar fornecedores",
            description = "Retorna lista de fornecedores (codigo 200) ou codigo 204 se nao houver fornecedores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fornecedores",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDto>> listar(
            @RequestParam(required = false) String razaoSocial
    ) {
        List<FornecedorResponseDto> fornecedores = FornecedorMapper.toResponseList(fornecedorService.filtrar(razaoSocial));

        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fornecedores);
    }

    @Operation(summary = "Buscar fornecedor pelo id",
            description = "Retorna o fornecedor (codigo 200)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> retornarPeloId(@PathVariable Integer id) {
        return ResponseEntity.ok(FornecedorMapper.toResponse(fornecedorService.retornarPeloId(id)));
    }

    @Operation(summary = "Pesquisar fornecedor por razao social",
            description = "Retorna lista de fornecedores (codigo 200) ou codigo 204 se nao houver fornecedores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fornecedores",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteudo")
            })
    @GetMapping("/pesquisa")
    public ResponseEntity<List<FornecedorResponseDto>> filtrar(@RequestParam String razaoSocial) {
        List<FornecedorResponseDto> fornecedores = FornecedorMapper.toResponseList(fornecedorService.filtrar(razaoSocial));

        if (fornecedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fornecedores);
    }

    @Operation(summary = "Cadastrar fornecedor",
            description = "Retorna fornecedor (codigo 201) ou codigo 409 se ja existir",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fornecedor",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<FornecedorResponseDto> cadastrar(@RequestBody @Valid FornecedorRequestDto fornecedor) {
        return ResponseEntity.status(201).body(FornecedorMapper.toResponse(fornecedorService.cadastrar(fornecedor)));
    }

    @Operation(summary = "Atualizar fornecedor",
            description = "Atualiza e retorna fornecedor (codigo 200)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Nao encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponseDto> atualizar(
            @PathVariable Integer id,
            @RequestBody @Valid FornecedorRequestDto fornecedor
    ) {
        return ResponseEntity.ok(FornecedorMapper.toResponse(fornecedorService.atualizar(id, fornecedor)));
    }

    @Operation(summary = "Deletar fornecedor",
            description = "Deleta fornecedor pelo id (codigo 204)",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteudo"),
                    @ApiResponse(responseCode = "404", description = "Nao encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
