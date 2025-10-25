package com.toomate.backend.controller;

import com.toomate.backend.dto.divida.DividaRequestDto;
import com.toomate.backend.dto.divida.DividaResponseDto;
import com.toomate.backend.dto.divida.DividaResponseModalDto;
import com.toomate.backend.model.Divida;
import com.toomate.backend.service.DividaService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dividas")
public class DividaController {

    private final DividaService dividaService;

    public DividaController(DividaService dividaService) {
        this.dividaService = dividaService;
    }

    @Operation(summary = "Cadastrar dividas",
            description = "Retorna a divida (codigo 201) ou lança uma exceção com codigo 409 se a divida já estiver cadastrada",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Divida",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping()
    public ResponseEntity<DividaResponseDto> cadastrar(@Valid @RequestBody DividaRequestDto divida){
    return ResponseEntity.status(201).body(dividaService.cadastrar(divida));
    }

    @Operation(summary = "Listar dividas",
            description = "Retorna uma lista de dividas (codigo 200) ou codigo 204 se não houver dívidas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de dividas",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping()
    public ResponseEntity<List<DividaResponseModalDto>> listarModal(){
        List<DividaResponseModalDto> dividas = dividaService.listarModal();

        if (dividas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(dividas);
    }

    @Operation(summary = "Atualizar estado da divida",
            description = "Retorna a divida (codigo 201) ou codigo 404 se não encontrar a dívida",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Divida",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/atualizarEstado/{idDivida}")
    public ResponseEntity<DividaResponseDto> atualizarEstado(@PathVariable Integer idDivida){

        if (dividaService.existePorId(idDivida)){
        return ResponseEntity.status(201).body(dividaService.atualizarEstado(idDivida));
        }
        return ResponseEntity.status(404).build();
    }

    @Operation(summary = "Atualizar divida",
            description = "Retorna o codigo 204 ou codigo 404 se não encontrar a dívida",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{idDivida}")
    public ResponseEntity<DividaResponseDto> atualizar(@PathVariable Integer idDivida, @Valid @RequestBody DividaRequestDto dividaRequestDto){
        if (dividaService.existePorId(idDivida)){
            dividaService.atualizar(idDivida, dividaRequestDto);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
