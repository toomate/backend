package com.toomate.backend.controller;

import com.toomate.backend.integration.S3Uploader;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.service.ArquivoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.List;

@RestController
@RequestMapping("/arquivos")
@SecurityRequirement(name = "Bearer")
public class ArquivoController {
    private final ArquivoService arquivoService;

    public ArquivoController(ArquivoService arquivoService) {
        this.arquivoService = arquivoService;
    }

    @GetMapping
    public ResponseEntity<List<Arquivo>> listar() {
        List<Arquivo> arquivos = arquivoService.listar();

        if (arquivos.isEmpty()) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(arquivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Arquivo> retornarPeloId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(arquivoService.buscarPorId(id));
    }

    @GetMapping("/{bucket}/{chave}")
    public ResponseEntity<byte[]> buscarArquivo(@PathVariable String bucket, @PathVariable String chave){
        return ResponseEntity.status(200).header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"imagem.png\"").contentType(MediaType.IMAGE_PNG).body(arquivoService.buscarArquivo(bucket, chave));
    }

    @PostMapping(value = "/{bucket}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Arquivo> cadastrar(@RequestParam("arquivo") MultipartFile arquivo, @PathVariable String bucket) {
        return ResponseEntity.status(201).body(arquivoService.cadastrarArquivo(arquivo, bucket));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        arquivoService.deletarArquivo(id);
        return ResponseEntity.status(204).build();
    }

    @PatchMapping("/{bucket}/{chave}")
    public ResponseEntity<byte[]> atualizar(@PathVariable String bucket, @PathVariable String chave, @RequestBody byte[] novoArquivo){
        return ResponseEntity.status(200).body(arquivoService.atualizarImagem(bucket, chave, novoArquivo));
    }
}
