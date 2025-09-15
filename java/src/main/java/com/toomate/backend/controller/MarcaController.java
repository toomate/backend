package com.toomate.backend.controller;

import com.toomate.backend.model.Marca;
import com.toomate.backend.repository.MarcaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/marcas")
public class MarcaController {
    private final MarcaRepository repository;

    public MarcaController(MarcaRepository repository){this.repository = repository;}

    @GetMapping
    public ResponseEntity<List<Marca>> listar(){
        List<Marca> marcaIngredientes = repository.findAll();
        if(marcaIngredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(marcaIngredientes);
    }

    @GetMapping("/por-descricao-valor")
    public ResponseEntity<List<Marca>> filtroNome(@RequestParam(required = false) String descricao, @RequestParam(required = false) Double valorMedida){
        List<Marca> marcaIngredientes = new ArrayList<>();
        if(descricao == null){
            marcaIngredientes = repository.findByValorMedida(valorMedida);
        }
        else if(valorMedida == null){
            marcaIngredientes = repository.findByDescricaoContainingIgnoreCase(descricao);
        }
        else{
            marcaIngredientes = repository.findByDescricaoContainingIgnoreCaseAndValorMedida(descricao, valorMedida);
        }
        if(marcaIngredientes.isEmpty()){
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(marcaIngredientes);
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody Marca marcaIngrediente){
        String descricao = marcaIngrediente.getDescricao();
        Double valorMedida = marcaIngrediente.getValorMedida();
        Integer fkIngrediente = marcaIngrediente.getFkIngrediente();
        Integer fkFornecedor = marcaIngrediente.getFkFornecedor();
        Optional<Marca> marcaIngredienteCheck = repository.findByDescricaoAndValorMedidaAndFkInsumoAndFkFornecedor(descricao, valorMedida, fkIngrediente, fkFornecedor);
        if(marcaIngredienteCheck.isPresent()){
            return ResponseEntity.status(409).build();
        }
        repository.save(marcaIngrediente);
        return ResponseEntity.status(201).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        if(repository.existsById(id)){
            repository.deleteById(id);
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizar(@PathVariable Integer id, @RequestBody Marca marcaIngrediente){
        marcaIngrediente.setIdMarca(id);
        if(repository.existsById(id)){
            repository.save(marcaIngrediente);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }
}
