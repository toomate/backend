package com.toomate.backend.controller;

import com.toomate.backend.dto.cliente.ClienteRequestDto;
import com.toomate.backend.dto.cliente.ClientesResponseDto;
import com.toomate.backend.model.Cliente;
import com.toomate.backend.service.ClienteService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }


    @Operation(summary = "Listar clientes",
            description = "Retorna lista de clientes (codigo 200) ou codigo 204 se não houver clientes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de clientes",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<ClientesResponseDto>> listar() {

        List<ClientesResponseDto> clientes = clienteService.listar();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(clientes);
    }

    @Operation(summary = "Cadastrar cliente",
            description = "Retorna o cliente (codigo 201) após o cadastro",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente",
                            content = @Content(mediaType = "application/json"))
            })
    @PostMapping
    public ResponseEntity<Cliente> cadastrar(@Valid ClienteRequestDto dto) {
        return ResponseEntity.status(201).body(clienteService.cadastrar(dto));
    }

    @Operation(summary = "Buscar cliente por id",
            description = "Retorna o cliente (codigo 200) ou codigo 404 se não encontrar o cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer idCliente) {

        Optional<Cliente> clienteOpt = clienteService.buscarPorId(idCliente);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return ResponseEntity.status(200).body(cliente);
        }

        return ResponseEntity.status(404).build();

    }

    @Operation(summary = "Atualizar cliente",
            description = "Retorna o cliente (codigo 200) ou lança uma exceção com código 404 se não encontrar o cliente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer idCliente, @Valid @RequestBody Cliente cliente) {
        return ResponseEntity.status(200).body(clienteService.atualizar(idCliente, cliente));
    }

    @Operation(summary = "Deletar cliente por id",
            description = "Retorna codigo 204 após o cliente ser deletado lança uma exceção com codigo 404 se não encontrar o cliente",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer idCliente) {
        clienteService.deletarPorId(idCliente);
        return ResponseEntity.status(204).build();
    }

}
