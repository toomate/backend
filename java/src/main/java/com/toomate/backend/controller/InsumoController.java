package com.toomate.backend.controller;

import com.toomate.backend.dto.insumo.InsumoMapperDto;
import com.toomate.backend.dto.insumo.InsumoRequestDto;
import com.toomate.backend.dto.insumo.InsumoResponseDto;
import com.toomate.backend.model.Categoria;
import com.toomate.backend.model.Insumo;
import com.toomate.backend.service.CategoriaService;
import com.toomate.backend.service.InsumoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/insumos")
@SecurityRequirement(name = "Bearer")
public class InsumoController {
    private final InsumoService insumoService;
    private final CategoriaService categoriaService;

    public InsumoController(InsumoService insumoService, CategoriaService categoriaService) {
        this.insumoService = insumoService;
        this.categoriaService = categoriaService;
    }

    @Operation(summary = "Listar insumo",
            description = "Retorna uma lista de insumos (codigo 200) ou codigo 204 se não houver insumos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de insumos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<InsumoResponseDto>> listar(){
        List<InsumoResponseDto> insumos = InsumoMapperDto.toDto(insumoService.listar());

        if(insumos.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(insumos);
    }

    @Operation(summary = "Listar insumos filtrados pelo nome",
            description = "Retorna uma lista de insumos(codigo 200) ou codigo 204 se não houver insumos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de insumos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/por-nome")
    public ResponseEntity<List<Insumo>> filtroNome(@RequestParam String nome){
        List<Insumo> insumos = insumoService.listarPorNome(nome);
        if(insumos.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(insumos);
    }

    @Operation(summary = "Cadastrar insumo",
            description = "Retorna o insumo(codigo 201) ou codigo 409 se o insumo já estiver cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Insumo",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<InsumoResponseDto> cadastrar(@Valid @RequestBody InsumoRequestDto insumo){
        if (insumoService.existePorNome(insumo.getNome())) {
            return ResponseEntity.status(409).build();
        }

        Categoria categoria = categoriaService.categoriaPorId(insumo.getFkCategoria());
        Insumo corpo = insumoService.cadastrar(insumo, categoria);

        InsumoResponseDto responseDto = InsumoMapperDto.toDto(corpo);
        return ResponseEntity.status(201).body(responseDto);
    }

    @Operation(summary = "Deletar insumo pelo id",
            description = "Retorna codigo 204 ou lança uma exceção com codigo 404 se não encontrar o insumo",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        insumoService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar insumo",
            description = "Retorna codigo 204 ou codigo 404 se não encontrar o insumo",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
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
