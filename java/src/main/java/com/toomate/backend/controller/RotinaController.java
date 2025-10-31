package com.toomate.backend.controller;

import com.toomate.backend.dto.rotina.RotinaRequestDto;
import com.toomate.backend.model.Rotina;
import com.toomate.backend.service.RotinaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    public ResponseEntity<List<Rotina>> listar(){
        List<Rotina> rotinas = rotinaService.listar();

        if (rotinas.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(rotinas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rotina> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.status(200).body(rotinaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Rotina> cadastrar(@RequestBody RotinaRequestDto request){
        return ResponseEntity.status(201).body(rotinaService.cadastrar(request));
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
}


