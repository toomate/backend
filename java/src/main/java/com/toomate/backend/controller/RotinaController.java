package com.toomate.backend.controller;

import com.toomate.backend.dto.rotina.PageResponseDto;
import com.toomate.backend.dto.rotina.RotinaInsumoRequest;
import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.model.Rotina;
import com.toomate.backend.model.RotinaInsumo;
import com.toomate.backend.service.RotinaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rotinas")
@SecurityRequirement(name = "Bearer")
public class RotinaController {
    private final RotinaService rotinaService;

    public RotinaController(RotinaService rotinaService) {
        this.rotinaService = rotinaService;
    }

    @GetMapping
    public ResponseEntity<PageResponseDto> listar(@RequestParam(defaultValue = "0") Integer pagina, @RequestParam(defaultValue = "16") Integer tamanho){
        Page<Rotina> rotinas = rotinaService.listarComPaginacao(pagina, tamanho);
        PageResponseDto pgResponse = PageResponseDto.de(rotinas);
        return ResponseEntity.status(200).body(pgResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rotina> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(rotinaService.buscarPorId(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Rotina>> pesquisarRotinas(@RequestParam String titulo){
        return ResponseEntity.status(200).body(rotinaService.pesquisar(titulo));
    }

    @PostMapping
    public ResponseEntity<Rotina> cadastrar(@RequestBody RotinaRequestDto request){
        return ResponseEntity.status(201).body(rotinaService.cadastrar(request));
    }

    @PutMapping("/associar-rotina-insumo")
    public ResponseEntity<List<RotinaInsumo>> associar(@RequestBody RotinaInsumoRequest request) {
        return ResponseEntity.status(201).body(rotinaService.associarInsumo(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rotina> atualizar(@PathVariable Integer id, @RequestBody RotinaRequestDto rotina){
        return ResponseEntity.status(200).body(rotinaService.atualizar(rotina, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        rotinaService.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/baixa/{id}")
    public ResponseEntity<Void> darBaixa(@PathVariable Integer id){
        rotinaService.darBaixa(id);
        return ResponseEntity.status(204).build();
    }
}


