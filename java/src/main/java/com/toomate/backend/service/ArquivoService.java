package com.toomate.backend.service;

import com.toomate.backend.dto.arquivo.ArquivoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.S3Uploader;
import com.toomate.backend.mapper.ArquivoMapper;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.repository.ArquivoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ArquivoService {
    private final ArquivoRepository arquivoRepository;

    public ArquivoService(ArquivoRepository arquivoRepository) {
        this.arquivoRepository = arquivoRepository;
    }

    public List<Arquivo> listar() {
        return arquivoRepository.findAll();
    }

    public Arquivo buscarPorId(Integer id) {
        return arquivoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("O arquivo com o id %d não foi encontrado!", id)));
    }

    public Arquivo cadastrarArquivo(MultipartFile arquivo, String bucket) {
        if (arquivo == null) {
            throw new EntradaInvalidaException("O arquivo é inválido");
        }
        String nomeArquivo = arquivo.getOriginalFilename();

        ArquivoRequestDto arquivoRequestDto = new ArquivoRequestDto(nomeArquivo, "Guinho", bucket, 1);

        String chave = String.format("%s_%d_%s", bucket, arquivoRequestDto.getIdBoleto(), nomeArquivo);

        arquivoRequestDto.setChave(chave);

        Arquivo arquivoParaCadastrar = ArquivoMapper.toEntity(arquivoRequestDto);

        try {
            byte[] imagem = arquivo.getBytes();
            S3Uploader.putImage(bucket, chave, imagem);
            return arquivoRepository.save(arquivoParaCadastrar);
        } catch (IOException e) {
            throw new EntradaInvalidaException("A imagem não pôde ser lida" + e.getMessage());
        }
    }

    public void deletarArquivo(Integer id) {
        Arquivo arquivo = arquivoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um arquivo com o id %d", id)));

        S3Uploader.deleteImage(arquivo.getNomeBucket(), arquivo.getChave());
        arquivoRepository.deleteById(id);
    }

    public byte[] buscarArquivo(String nomeBucket, String nomeArquivo) {
        return S3Uploader.getImage(nomeBucket, nomeArquivo);
    }

    public byte[] atualizarImagem(String nomeBucket, String nomeArquivo, byte[] novaImagem){
        Arquivo arquivo = arquivoRepository.findByChave(nomeArquivo).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um arquivo com a chave %s", nomeArquivo)));

        S3Uploader.updateImage(nomeBucket, nomeArquivo, novaImagem);

        arquivo.setDtAlteracao(LocalDate.now());
        arquivoRepository.save(arquivo);

        return novaImagem;
    }

}
