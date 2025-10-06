package com.toomate.backend.controller;

import com.toomate.backend.dto.lote.LoteMapperDto;
import com.toomate.backend.dto.lote.LoteRequestDto;
import com.toomate.backend.dto.lote.LoteResponseDto;
import com.toomate.backend.dto.marca.MarcaMapperDto;
import com.toomate.backend.dto.marca.MarcaResponseDto;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.LoteRepository;
import com.toomate.backend.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lotes")
public class LoteController {
    private final LoteService loteService;
    private final UsuarioService usuarioService;
    private final MarcaService marcaService;

    public LoteController(LoteService loteService, UsuarioService usuarioService, MarcaService marcaService) {
        this.loteService = loteService;
        this.usuarioService = usuarioService;
        this.marcaService = marcaService;
    }

    @GetMapping
    public ResponseEntity<List<LoteResponseDto>> listar(){
        List<LoteResponseDto> lote = LoteMapperDto.toDto(loteService.listar());

        if(lote.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lote);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDto> pegarPorId(@PathVariable Integer id){
        Lote lote = loteService.listarPorId(id);

        LoteResponseDto loteDto = LoteMapperDto.toDto(lote);
        return ResponseEntity.status(200).body(loteDto);
    }

    @PostMapping
    public ResponseEntity<LoteResponseDto> cadastrar(@RequestBody LoteRequestDto lote){
        if(lote == null){
            return null;
        }

        Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
        Marca marca = marcaService.marcaPorId(lote.getFkMarca());
        Lote corpo = loteService.cadastrar(lote, usuario, marca);

        LoteResponseDto loteResponse = LoteMapperDto.toDto(corpo);
        return ResponseEntity.status(201).body(loteResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
       loteService.deletar(id);
       return ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@RequestBody LoteRequestDto lote, @PathVariable Integer id){

        if (loteService.existePorId(id)) {
            Usuario usuario = usuarioService.usuarioPorId(lote.getFkUsuario());
            Marca marca = marcaService.marcaPorId(lote.getFkMarca());
            Lote loteAtualizado = LoteMapperDto.toEntity(lote, usuario, marca);
            loteService.atualizar(id, loteAtualizado);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

}
