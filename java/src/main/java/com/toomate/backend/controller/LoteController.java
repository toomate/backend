package com.toomate.backend.controller;

import com.toomate.backend.dto.estoque_grupo.EstoqueGrupo;
import com.toomate.backend.dto.lote.LoteMapperDto;
import com.toomate.backend.dto.lote.LotePatchDto;
import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.model.*;
import com.toomate.backend.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotes")
@SecurityRequirement(name = "Bearer")
public class LoteController {
    private final LoteService loteService;
    private final UsuarioService usuarioService;
    private final MarcaService marcaService;

    public LoteController(LoteService loteService, UsuarioService usuarioService, MarcaService marcaService) {
        this.loteService = loteService;
        this.usuarioService = usuarioService;
        this.marcaService = marcaService;
    }


    @Operation(summary = "Listar lote",
            description = "Retorna uma lista de lotes(codigo 200) ou codigo 204 se não houver lotes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de lotes",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo")
            })
    @GetMapping
    public ResponseEntity<List<LoteResponseDto>> listar() {
        List<LoteResponseDto> lote = LoteMapperDto.toDto(loteService.listar());

        if (lote.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lote);
    }

    @Operation(summary = "Busca um lote pelo id",
            description = "Retorna um lote(codigo 200) ou lança um exceção com código 404 se não encontrar o lote",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lote",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDto> pegarPorId(@PathVariable Integer id) {
        Lote lote = loteService.listarPorId(id);

        LoteResponseDto loteDto = LoteMapperDto.toDto(lote);
        return ResponseEntity.status(200).body(loteDto);
    }

    @GetMapping("/estoque")
    public ResponseEntity<List<EstoqueGrupo>> buscarEstoque() {
        List<EstoqueGrupo> estoque = loteService.buscarEstoque();

        if (estoque.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @GetMapping("/estoque/{categoria}")
    public ResponseEntity<List<EstoqueGrupo>> buscarEstoquePorCategoria(@PathVariable String categoria) {
        List<EstoqueGrupo> estoque = loteService.buscarEstoquePorCategoria(categoria);

        if (estoque.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @GetMapping("/estoque/search")
    public ResponseEntity<List<EstoqueGrupo>> pesquisarEstoquePorInsumo(@RequestParam String insumo) {
        List<EstoqueGrupo> estoque = loteService.pesquisarEstoquePorInsumo(insumo);

        if (estoque.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(estoque);
    }

    @PatchMapping
    public ResponseEntity<Void> atualizarEstoque(@RequestBody List<LotePatchDto> requests){
        loteService.atualizarQuantidades(requests);

        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Cadastrar lote",
            description = "Retorna o lote cadastrado(codigo 201) ou lança uma exceção de codigo 409 se o lote já estiver cadastrado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Lote cadastrado",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "409", description = "Conflito no cadastro")
            })
    @PostMapping
    public ResponseEntity<LoteResponseDto> cadastrar(@RequestBody LoteRequestDto lote) {
        if (lote == null) {
            return null;
        }
        Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
        Marca marca = marcaService.marcaPorId(lote.getFkMarca());
        Lote corpo = loteService.cadastrar(LoteMapperDto.toEntity(lote, usuario, marca));

        LoteResponseDto loteResponse = LoteMapperDto.toDto(corpo);
        return ResponseEntity.status(201).body(loteResponse);
    }

    @Operation(summary = "Deletar lote por id",
            description = "Retorna o codigo 204 ou lança uma exceção de codigo 404 se o lote não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        loteService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar lote",
            description = "Retorna o código 204 ou lança uma exceção de codigo 404 se o lote não for encontrado",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Sem conteúdo"),
                    @ApiResponse(responseCode = "404", description = "Não encontrado")
            })
    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@RequestBody LoteRequestDto lote, @PathVariable Integer id) {

        if (loteService.existePorId(id)) {
            Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
            Marca marca = marcaService.marcaPorId(lote.getFkMarca());
            Lote loteAtualizado = LoteMapperDto.toEntity(lote, usuario, marca);
            loteService.atualizar(id, loteAtualizado);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/adicionarEstoque/{id}")
    public ResponseEntity<Lote> adicionar(@RequestBody Double quantidadeMedida, @PathVariable Integer id) {
        if (loteService.existePorId(id)) {
            loteService.adicionarQuantidade(id, quantidadeMedida);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/removerEstoque/{id}")
    public ResponseEntity<Lote> remover(@RequestBody Double quantidadeMedida, @PathVariable Integer id) {
        if (loteService.existePorId(id)) {
            loteService.removerQuantidade(id, quantidadeMedida);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(404).build();
    }

}
