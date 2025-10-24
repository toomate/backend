package com.toomate.backend.controller;

import com.toomate.backend.dto.divida.DividaRequestDto;
import com.toomate.backend.dto.divida.DividaResponseModalDto;
import com.toomate.backend.model.Divida;
import com.toomate.backend.service.DividaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dividas")
public class DividaController {

    private final DividaService dividaService;

    public DividaController(DividaService dividaService) {
        this.dividaService = dividaService;
    }

    @PostMapping()
    public ResponseEntity<Divida> cadastrar(@Valid @RequestBody DividaRequestDto divida){
    return ResponseEntity.status(201).body(dividaService.cadastrar(divida));
    }

    @GetMapping()
    public ResponseEntity<List<DividaResponseModalDto>> listarModal(){
        List<DividaResponseModalDto> dividas = dividaService.listarModal();

        if (dividas.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(dividas);
    }

    @PutMapping("/atualizarEstado/{idDivida}")
    public ResponseEntity<Divida> atualizarEstado(@PathVariable Integer idDivida){

        if (dividaService.existePorId(idDivida)){
        return ResponseEntity.status(201).body(dividaService.atualizarEstado(idDivida));
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{idDivida}")
    public ResponseEntity<Divida> atualizar(@PathVariable Integer idDivida, @Valid @RequestBody DividaRequestDto dividaRequestDto){
        if (dividaService.existePorId(idDivida)){
            return ResponseEntity.status(201).body(dividaService.atualizar(idDivida, dividaRequestDto));
        }
        return ResponseEntity.status(404).build();
    }

}
