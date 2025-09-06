package com.toomate.backend.controller;


import com.toomate.backend.model.Cliente;
import com.toomate.backend.repository.ClienteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository ;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {

        List<Cliente> clientes = clienteRepository.findAll();

        if (clientes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(201).body(clientes);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer idCliente) {

        Optional<Cliente> clienteOpt = clienteRepository.findById(idCliente);

        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            return ResponseEntity.status(200).body(cliente);
        }

        return ResponseEntity.status(404).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Integer idCliente, @RequestBody Cliente cliente) {

        cliente.setIdCliente(idCliente);

        if (clienteRepository.existsById(idCliente)) {
            Cliente save = clienteRepository.save(cliente);
            return ResponseEntity.status(200).body(cliente);
        }

        return ResponseEntity.status(404).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Integer idCliente) {

        if (clienteRepository.existsById(idCliente)) {
            clienteRepository.deleteById(idCliente);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();

    }

    @GetMapping("/por-nome")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam String nome) {

        List<Cliente> clientesEncontrados = clienteRepository.findByNomeContainingIgnoreCase(nome);

        if (clientesEncontrados.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(clientesEncontrados);

    }

}
