package com.toomate.backend.service;

import com.toomate.backend.dto.arquivo_relacionamento.ArquivoRelacionamentoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.ErroUploadException;
import com.toomate.backend.integration.S3Uploader;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.repository.ArquivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ArquivoService {
    private final ArquivoRepository arquivoRepository;
    private final ArquivoRelacionamentoService relacionamentoService;

    public ArquivoService(ArquivoRepository arquivoRepository, ArquivoRelacionamentoService relacionamentoService) {
        this.arquivoRepository = arquivoRepository;
        this.relacionamentoService = relacionamentoService;
    }

    public List<Arquivo> listar() {
        return arquivoRepository.findAll();
    }

    public Arquivo buscarPorId(Integer id) {
        return arquivoRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("O arquivo com o id %d não foi encontrado!", id)));
    }

    @Transactional
    public Arquivo cadastrarArquivo(MultipartFile arquivo, String bucket, ArquivoRelacionamentoRequestDto relacionamento) {
        if (arquivo == null) {
            throw new EntradaInvalidaException("O arquivo não pode ser nulo!");
        }
        relacionamentoService.validarEntidade(relacionamento);

        String nome = limparNome(arquivo.getOriginalFilename());

        String chave = gerarChave(nome);

        uploadArquivo(arquivo, bucket, chave);

        Arquivo entidade = criarEntidade(nome, chave, bucket);
        Arquivo salvo = arquivoRepository.save(entidade);

        relacionamentoService.relacionar(salvo, relacionamento);

        return salvo;
    }

    private String limparNome(String nome) {
        return Objects.requireNonNull(nome.replace(" ", ""));
    }

    private String gerarChave(String nome) {
        return UUID.randomUUID() + "_" + Objects.requireNonNull(nome);
    }

    private Arquivo criarEntidade(String nome, String chave, String bucket) {
        Arquivo arquivo = new Arquivo();
        arquivo.setNomeBucket(bucket);
        arquivo.setNomeOriginal(nome);
        arquivo.setChave(chave);
        arquivo.setDtCriacao(LocalDate.now());

        return arquivo;
    }

    private void uploadArquivo(MultipartFile arquivo, String bucket, String chave) {
        try {
            byte[] imagem = arquivo.getBytes();
            S3Uploader.putImage(bucket, chave, imagem);
        } catch (IOException e) {
            throw new ErroUploadException("A imagem não pôde ser lida" + e.getMessage());
        }
    }

    public void deletarArquivo(Integer id) {
        Arquivo arquivo = arquivoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um arquivo com o id %d", id)));

        S3Uploader.deleteImage(arquivo.getNomeBucket(), arquivo.getChave());
        relacionamentoService.deletar(arquivo);
        arquivoRepository.deleteById(id);
    }

    public byte[] buscarArquivo(String nomeBucket, String nomeArquivo) {
        return S3Uploader.getImage(nomeBucket, nomeArquivo);
    }

    public byte[] atualizarImagem(String nomeBucket, String nomeArquivo, byte[] novaImagem) {
        Arquivo arquivo = arquivoRepository.findByChave(nomeArquivo).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado um arquivo com a chave %s", nomeArquivo)));

        S3Uploader.updateImage(nomeBucket, nomeArquivo, novaImagem);

        arquivo.setDtAlteracao(LocalDate.now());
        arquivoRepository.save(arquivo);


        return S3Uploader.getImage(nomeBucket, nomeArquivo);
    }

}
