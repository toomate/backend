package com.toomate.backend.controller;

import com.toomate.backend.model.Usuario;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> pegar(@PathVariable Integer id) {
        return ResponseEntity.of(usuarioRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {
        if (usuario == null) {
            return ResponseEntity.status(400).build();
        }

        if (usuarioRepository.existsByNome(usuario.getNome())) {
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(201).body((String.format("/usuarios/%d", usuarioRepository.save(usuario).getId())));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        if (usuario.getId() != null && !usuario.getId().equals(id)) {
            return ResponseEntity.status(400).build();
        }

        Optional<Usuario> usuarioParaAtualizar = usuarioRepository.findById(id);

        if (usuarioParaAtualizar.isPresent()) {
            usuario.setId(id);
            return ResponseEntity.status(200).body(usuarioRepository.save(usuario));
        }

        return ResponseEntity.status(404).build();
    }

}
