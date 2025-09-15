package com.toomate.backend.controller;

import com.toomate.backend.model.Lote;
import com.toomate.backend.repository.LoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lotes")
public class LoteController {
    private final LoteRepository loteRepository;

    public LoteController(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    @GetMapping
    public ResponseEntity<List<Lote>> listar(){
        List<Lote> lotes = loteRepository.findAll();

        if (lotes.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(lotes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lote> pegarPorId(@PathVariable Integer id){
        return ResponseEntity.of(loteRepository.findById(id));
    }

    @PostMapping
    public ResponseEntity<Lote> cadastrar(@RequestBody Lote lote){
        if(lote == null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.status(201).body(loteRepository.save(lote));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        if (loteRepository.existsById(id)){
            loteRepository.deleteById(id);
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lote> atualizar(@RequestBody Lote lote){
        if(loteRepository.existsById(lote.getIdLote())){
            return ResponseEntity.status(200).body(loteRepository.save(lote));
        }

        return ResponseEntity.status(404).build();
    }

}
