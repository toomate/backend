package com.toomate.backend.controller;

import com.toomate.backend.dto.usuario.*;
import com.toomate.backend.mapper.usuario.UsuarioMapper;
import com.toomate.backend.model.Usuario;
import com.toomate.backend.service.UsuarioService;
import io.swagger.oas.annotations.Operation;
import io.swagger.oas.annotations.media.Content;
import io.swagger.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Listar usuários",
            description = "Retorna uma lista de usuários (código 200) ou código 204 se não houver conteúdo",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> listar() {
        List<UsuarioResponseDto> usuarios = usuarioService.listar();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(usuarios);
    }

    @Operation(summary = "Buscar usuário pelo id",
            description = "Retorna o usuário (código 200) ou lança uma exceção de código 404 se não encontrar o usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> buscarPeloId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(usuarioService.buscarPorId(id));
    }

    @Operation(summary = "Buscar usuário pelo nome",
            description = "Retorna o usuário (código 200) ou lança uma exceção de código 404 se não encontrar o usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @GetMapping("/nome")
    public ResponseEntity<UsuarioResponseDto> buscarPeloNome(@RequestParam String nome) {
        return ResponseEntity.status(200).body(usuarioService.buscarPorNome(nome));
    }

    @Operation(summary = "Cadastrar usuário",
            description = "Retorna o usuário (código 201) ou lança uma exceção de código 409 se o usuário já estiver cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> cadastrar(@RequestBody @Valid UsuarioRequestDto request) {

        return ResponseEntity.status(201).body(usuarioService.cadastrar(request));
    }

    @Operation(summary = "Realizar login do usuário",
            description = "Retorna o token usuário (código 200) ou lança uma exceção de código 404 se o usuário não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token do usuário",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PostMapping("/login")
    @SecurityRequirement(name = "Bearer")
    public ResponseEntity<UsuarioTokenDto> login(@RequestBody @Valid UsuarioLoginDto usuarioLoginDto){
        final Usuario usuario = UsuarioMapper.of(usuarioLoginDto);
        UsuarioTokenDto usuarioTokenDto = this.usuarioService.autenticar(usuario);

        return ResponseEntity.status(200).body(usuarioTokenDto);
    }

    @Operation(summary = "Deletar o usuário",
            description = "Retorna o código 204 ou lança uma exceção de código 404 se o usuário não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        usuarioService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar o usuário",
            description = "Retorna o usuário (código 204) ou lança uma exceção de código 404 se o usuário não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.status(200).body(usuarioService.atualizar(id, usuario));
    }

    @Operation(summary = "Atualizar o usuário",
            description = "Retorna o usuário (código 200) ou lança uma exceção de código 404 se o usuário não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> atualizarAdministrador(@PathVariable Integer id, @RequestBody AtualizarAdministradorDto administrador) {
        return ResponseEntity.status(200).body(usuarioService.atualizarAdministrador(id, administrador));
    }

}
