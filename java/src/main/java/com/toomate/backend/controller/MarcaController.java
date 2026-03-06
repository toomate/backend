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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas")
@SecurityRequirement(name = "Bearer")
public class MarcaController {
    private final MarcaService marcaService;
    private final FornecedorService fornecedorService;
    private final InsumoService insumoService;

    public MarcaController(MarcaService marcaService, FornecedorService fornecedorService, InsumoService insumoService) {
        this.marcaService = marcaService;
        this.fornecedorService = fornecedorService;
        this.insumoService = insumoService;
    }

    @Operation(summary = "Listar marcas",
            description = "Retorna uma lista de marcas(código 200) ou codigo 204 se não houver marcas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de marcas",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<MarcaResponseDto>> listar(){
        List<MarcaResponseDto> marcas = MarcaMapperDto.toDto(marcaService.listar());

        if(marcas.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(marcas);

    }

    @Operation(summary = "Listar marcas filtrado por nome",
            description = "Retorna uma lista de marcas(código 200) ou codigo 204 se não houver marcas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de marcas",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/por-nome")
    public ResponseEntity<List<Marca>> filtroNome(@RequestParam String nome) {
        List<Marca> marca = marcaService.listarPorNome(nome);
        if (marca.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(marca);
    }

    @Operation(summary = "Cadastrar marca",
            description = "Retorna a marca cadastrada (código 201) ou codigo 409 se a marca já estiver cadastrada",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Marca cadastrada",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<MarcaResponseDto> cadastrar(@Valid @RequestBody MarcaRequestDto request){
        Marca entity = marcaService.cadastrar(request);

        MarcaResponseDto responseDto = MarcaMapperDto.toDto(entity);
        return ResponseEntity.status(201).body(responseDto);
    }

    @Operation(summary = "Deletar marca por id",
            description = "Retorna o código 204 ou lança uma exceção de codigo 404 se a marca não for encontrada",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        marcaService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar marca",
            description = "Retorna o código 204 ou codigo 404 se a marca não for encontrada",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody MarcaRequestDto marca){

        if (marcaService.existePorId(id)) {
            Fornecedor fornecedor = fornecedorService.retornarPeloId(marca.getFkFornecedor());
            Insumo insumo = insumoService.insumoPorId(marca.getFkInsumo());
            Marca marcaAtualizada = MarcaMapperDto.toEntity(marca, insumo, fornecedor);
            marcaService.atualizar(id, marcaAtualizada);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }
}
