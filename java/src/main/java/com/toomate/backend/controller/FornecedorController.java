package com.toomate.backend.controller;

import com.toomate.backend.dto.fornecedor.FornecedorRequestDto;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.repository.FornecedorRepository;
import com.toomate.backend.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fornecedores")
@SecurityRequirement(name = "Bearer")
public class FornecedorController {
    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @Operation(summary = "Listar fornecedores",
            description = "Retorna uma lista de fornecedores (codigo 200) ou codigo 204 se não houver fornecedores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fornecedores",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listar() {
        List<Fornecedor> fornecedores = fornecedorService.listar();

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @Operation(summary = "Buscar fornecedor pelo id",
            description = "Retorna o fornecedor (codigo 200)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor",
                            content = @Content(mediaType = "application/json"))
            })

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> retornarPeloId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(fornecedorService.retornarPeloId(id));
    }


    @Operation(summary = "Retorna uma lista de fornecedores filtrados pela razão social",
            description = "Retorna uma lista de fornecedores (codigo 200) ou codigo 204 se não houver fornecedores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de fornecedores",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/pesquisa")
    public ResponseEntity<List<Fornecedor>> filtrar(@RequestParam String razaoSocial) {
        List<Fornecedor> fornecedores = fornecedorService.filtrar(razaoSocial);

        if (fornecedores.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(fornecedores);
    }

    @Operation(summary = "Cadastrar fornecedor",
            description = "Retorna fornecedor (codigo 201) ou lança uma exceção com codigo 409 se o fornecedor já estiver cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Fornecedor",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })

    @PostMapping
    public ResponseEntity<Fornecedor> cadastrar(@RequestBody @Valid FornecedorRequestDto fornecedor) {
        return ResponseEntity.status(201).body(fornecedorService.cadastrar(fornecedor));
    }

    @Operation(summary = "Atualizar fornecedor",
            description = "Atualizar fornecedor (codigo 200) ou lança uma exceção com codigo 404 se o fornecedor não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Fornecedor",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })

    @PutMapping("/{id}")
    public ResponseEntity<Fornecedor> atualizar(@PathVariable Integer id, @RequestBody Fornecedor fornecedor) {
        return ResponseEntity.status(200).body(fornecedorService.atualizar(id, fornecedor));
    }

    @Operation(summary = "Deletar fornecedor",
            description = "Deleta fornecedor pelo id (codigo 204) ou lança uma exceção com codigo 404 se o fornecedor não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        fornecedorService.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
