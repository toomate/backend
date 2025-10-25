package com.toomate.backend.controller;

import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.repository.BoletoRepository;
import com.toomate.backend.service.BoletoService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.ArraySchema;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.media.Schema;
import io.swagger.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boletos")
public class BoletoController {

    private final BoletoService boletoService;

    public BoletoController(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @Operation(summary = "Cadastrar boleto",
            description = "Retorna o boleto (codigo 201) após o cadastro",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Boleto",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    public ResponseEntity<Boleto> cadastrar(@RequestBody BoletoRequestDto request) {
        return ResponseEntity.status(201).body(boletoService.cadastrar(request));
    }

    @Operation(summary = "Listar ingredientes",
            description = "Retorna lista de boletos (codigo 200) ou codigo 204 se não houver boletos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de boletos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<Boleto>> listar() {

        List<Boleto> boletos = boletoService.listarBoletos();

        if (boletos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletos);

    }

    @Operation(summary = "Buscar boleto por id",
            description = "Retorna boleto (codigo 200)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Boleto",
                            content = @Content(mediaType = "application/json"))
            })
    @GetMapping("/{id}")
    public ResponseEntity<Boleto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(boletoService.buscarPorId(id));
    }

    @Operation(summary = "Editar boleto",
            description = "Retorna boleto(codigo 200) após editar o boleto",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Boleto",
                            content = @Content(mediaType = "application/json"))
            })
    @PutMapping("/{id}")
    public ResponseEntity<Boleto> editar(@PathVariable Integer id, @RequestBody Boleto boleto) {

        return ResponseEntity.status(200).body(boletoService.editar(id, boleto));
    }

    @Operation(summary = "Deletar boleto por id",
            description = "Retorna codigo 204 após deletar o boleto",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @DeleteMapping("/{id}")
    public  ResponseEntity<Void> deletarPorId(@PathVariable Integer id) {
        boletoService.deletarPorId(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Buscar boletos por categoria",
            description = "Retorna lista de boletos (codigo 200) ou codigo 204 se não houver boletos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de boletos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/por-categoria")
    public ResponseEntity<List<Boleto>> buscarPorCategoria(@RequestParam String categoria) {

        List<Boleto> boletosEncontrados = boletoService.buscarPorCategoria(categoria);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);

    }


    @Operation(summary = "Buscar boletos pelo id do fornecedor",
            description = "Retorna lista de boletos (codigo 200) ou codigo 204 se não houver boletos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de boletos",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping("/fornecedores/{idFornecedor}")
    public ResponseEntity<List<Boleto>> buscarPorFornecedor(@PathVariable Integer idFornecedor) {

        List<Boleto> boletosEncontrados = boletoService.buscarPorFornecedor(idFornecedor);

        if (boletosEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(boletosEncontrados);
    }

}
