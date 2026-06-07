package com.toomate.backend.service;

import com.toomate.backend.dto.arquivo.ArquivoComprovanteResponseDto;
import com.toomate.backend.dto.arquivo_relacionamento.ArquivoRelacionamentoRequestDto;
import com.toomate.backend.enums.CategoriaComprovante;
import com.toomate.backend.enums.TipoEntidade;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.ErroUploadException;
import com.toomate.backend.integration.S3Uploader;
import com.toomate.backend.model.Arquivo;
import com.toomate.backend.model.ArquivoRelacionamento;
import com.toomate.backend.model.Divida;
import com.toomate.backend.model.Lote;
import com.toomate.backend.model.Marca;
import com.toomate.backend.repository.ArquivoRepository;
import com.toomate.backend.repository.DividaRepository;
import com.toomate.backend.repository.LoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArquivoService {
    private final ArquivoRepository arquivoRepository;
    private final ArquivoRelacionamentoService relacionamentoService;
    private final DividaRepository dividaRepository;
    private final LoteRepository loteRepository;

    public ArquivoService(ArquivoRepository arquivoRepository, ArquivoRelacionamentoService relacionamentoService,
                          DividaRepository dividaRepository, LoteRepository loteRepository) {
        this.arquivoRepository = arquivoRepository;
        this.relacionamentoService = relacionamentoService;
        this.dividaRepository = dividaRepository;
        this.loteRepository = loteRepository;
    }

    public List<Arquivo> listar() {
        return arquivoRepository.findAll();
    }

    public List<ArquivoComprovanteResponseDto> listarComprovantes() {
        List<ArquivoRelacionamento> relacionamentos = relacionamentoService.listarTodos().stream()
                .filter(relacionamento -> relacionamento.getArquivo() != null)
                .toList();

        List<Integer> idsDivida = idsPorTipo(relacionamentos, TipoEntidade.DIVIDA);
        List<Integer> idsLote = idsPorTipo(relacionamentos, TipoEntidade.LOTE);

        Map<Integer, Divida> dividasPorId = dividaRepository.findAllById(idsDivida).stream()
                .collect(Collectors.toMap(Divida::getIdDivida, Function.identity()));

        Map<Integer, Lote> lotesPorId = loteRepository.findAllById(idsLote).stream()
                .collect(Collectors.toMap(Lote::getIdLote, Function.identity()));

        return relacionamentos.stream()
                .map(relacionamento -> montarComprovante(relacionamento, dividasPorId, lotesPorId))
                .toList();
    }

    private List<Integer> idsPorTipo(List<ArquivoRelacionamento> relacionamentos, TipoEntidade tipo) {
        return relacionamentos.stream()
                .filter(rel -> tipo.getTipo().equalsIgnoreCase(rel.getTipoEntidade()))
                .map(ArquivoRelacionamento::getIdEntidade)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    private ArquivoComprovanteResponseDto montarComprovante(ArquivoRelacionamento relacionamento,
                                                            Map<Integer, Divida> dividasPorId,
                                                            Map<Integer, Lote> lotesPorId) {
        Arquivo arquivo = relacionamento.getArquivo();
        String tipo = relacionamento.getTipoEntidade() == null ? "" : relacionamento.getTipoEntidade();

        ArquivoComprovanteResponseDto dto = new ArquivoComprovanteResponseDto();
        dto.setId(arquivo.getId());
        dto.setNomeOriginal(arquivo.getNomeOriginal());
        dto.setChave(arquivo.getChave());
        dto.setNomeBucket(arquivo.getNomeBucket());
        dto.setDtCriacao(arquivo.getDtCriacao());
        dto.setTipoEntidade(relacionamento.getTipoEntidade());
        dto.setIdEntidade(relacionamento.getIdEntidade());
        dto.setCategoria(relacionamento.getCategoria());

        if (TipoEntidade.DIVIDA.getTipo().equalsIgnoreCase(tipo)) {
            if (dto.getCategoria() == null) {
                dto.setCategoria(CategoriaComprovante.CONSUMO.name());
            }
            Divida divida = dividasPorId.get(relacionamento.getIdEntidade());
            if (divida != null) {
                String cliente = divida.getCliente() != null ? divida.getCliente().getNome() : null;
                dto.setTitulo(cliente != null ? cliente : "Cliente não informado");
                dto.setGrupo(cliente != null ? cliente : "Cliente não informado");
                dto.setSubtitulo(divida.getPedido());
                dto.setDataReferencia(divida.getDataCompra());
                dto.setValor(divida.getValor());
                dto.setPago(divida.getPago());
            }
        } else if (TipoEntidade.LOTE.getTipo().equalsIgnoreCase(tipo)) {
            if (dto.getCategoria() == null) {
                dto.setCategoria(CategoriaComprovante.NOTA_FISCAL.name());
            }
            Lote lote = lotesPorId.get(relacionamento.getIdEntidade());
            if (lote != null) {
                Marca marca = lote.getMarca();
                String insumo = marca != null && marca.getInsumo() != null ? marca.getInsumo().getNome() : null;
                String nomeMarca = marca != null ? marca.getNomeMarca() : null;
                String fornecedor = marca != null && marca.getFornecedor() != null
                        ? marca.getFornecedor().getRazaoSocial() : null;

                dto.setTitulo(insumo != null ? insumo : "Insumo não informado");
                dto.setSubtitulo(montarSubtituloCompra(nomeMarca, fornecedor));
                dto.setGrupo(fornecedor != null ? fornecedor : "Fornecedor não informado");
                dto.setDataReferencia(lote.getDataEntrada());
                dto.setValor(calcularValorLote(lote));
            }
        }

        if (dto.getTitulo() == null) {
            dto.setTitulo(arquivo.getNomeOriginal());
        }
        if (dto.getGrupo() == null) {
            dto.setGrupo("Outros");
        }

        return dto;
    }

    private String montarSubtituloCompra(String marca, String fornecedor) {
        if (marca != null && fornecedor != null) {
            return marca + " · " + fornecedor;
        }
        if (marca != null) {
            return marca;
        }
        return fornecedor;
    }

    private Double calcularValorLote(Lote lote) {
        Double preco = lote.getPrecoUnitario();
        if (preco == null) {
            return null;
        }
        // Total verdadeiro da compra: precoUnit * quantidadeOriginal (qtd comprada).
        // Lotes antigos sem quantidadeOriginal caem na quantidadeMedida.
        Double quantidade = lote.getQuantidadeOriginal() != null
                ? lote.getQuantidadeOriginal().doubleValue()
                : lote.getQuantidadeMedida();
        if (quantidade == null) {
            return preco;
        }
        return preco * quantidade;
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
