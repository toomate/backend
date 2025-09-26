package com.toomate.backend.controller;

import com.toomate.backend.dto.usuario.AtualizarAdministradorDto;
import com.toomate.backend.dto.usuario.UsuarioRequestDto;
import com.toomate.backend.dto.usuario.UsuarioResponseDto;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<UsuarioResponseDto> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(usuarioService.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<UsuarioResponseDto> buscarPeloEmail(@RequestParam String email) {
        return ResponseEntity.status(200).body(usuarioService.buscarPorEmail(email));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@RequestBody @Valid UsuarioRequestDto request) {
        return ResponseEntity.status(201).body(usuarioService.cadastrar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(204).build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.status(200).body(usuarioService.atualizar(id, usuario));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarAdministrador(@PathVariable Integer id, @RequestBody AtualizarAdministradorDto administrador) {
        return ResponseEntity.status(200).body(usuarioService.atualizarAdministrador(id, administrador));
    }

}
