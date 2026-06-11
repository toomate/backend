package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.rotina.*;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.mapper.rotina.RotinaMapper;
import com.toomate.backend.model.*;
import com.toomate.backend.repository.RotinaInsumoRepository;
import com.toomate.backend.repository.RotinaRepository;
import com.toomate.backend.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RotinaService {
    private final RotinaRepository rotinaRepository;
    private final InsumoService insumoService;
    private final RotinaInsumoRepository rotinaInsumoRepository;
    private final LoteService loteService;
    private final MarcaService marcaService;
    private final AuditService auditService;
    private final UsuarioRepository usuarioRepository;

    public RotinaService(RotinaRepository rotinaRepository, InsumoService insumoService, RotinaInsumoRepository rotinaInsumoRepository, LoteService loteService, MarcaService marcaService, AuditService auditService, UsuarioRepository usuarioRepository) {
        this.rotinaRepository = rotinaRepository;
        this.insumoService = insumoService;
        this.rotinaInsumoRepository = rotinaInsumoRepository;
        this.loteService = loteService;
        this.marcaService = marcaService;
        this.auditService = auditService;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Rotina> listar() {
        return rotinaRepository.findAll();
    }

    public Page<Rotina> listarComPaginacao(Integer pagina, Integer tamanho, String titulo) {
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);
        if (titulo == null || titulo.isBlank()) {
            return rotinaRepository.findAll(pgRequest);
        }

        return rotinaRepository.findAllByTituloContainingIgnoreCase(pgRequest, titulo);
    }

    public Rotina buscarPorId(Integer id) {
        return rotinaRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id)));
    }

    public List<Rotina> pesquisar(String nomeRotina) {
        return rotinaRepository.findByTituloContainsIgnoreCase(nomeRotina);
    }

    public Rotina cadastrar(RotinaRequestDto request) {
        if (request == null) {
            throw new EntradaInvalidaException("A rotina não pode ser nula!");
        }
        Rotina rotina = RotinaMapper.toEntity(request);
        rotinaRepository.save(rotina);
        auditService.registrar(getUsuarioLogado(), "CADASTRO", "ROTINA", "Cadastrou rotina: " + rotina.getTitulo());
        return rotina;
    }

    @Transactional
    public List<RotinaInsumo> associarInsumo(RotinaInsumoRequest request) {
        List<InsumoRotina> insumos = request.getInsumos();
        Rotina rotina = rotinaRepository.findById(request.getFkRotina()).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com o id %d", request.getFkRotina())));

        List<RotinaInsumo> rotinas = new ArrayList<>();

        for (InsumoRotina atual : insumos) {
            Insumo insumo = insumoService.insumoPorId(atual.getInsumoId());
            RotinaInsumo rotinaInsumo = new RotinaInsumo();
            rotinaInsumo.setInsumo(insumo);
            rotinaInsumo.setQuantidadeInsumo(Math.abs(atual.getQuantidadeInsumo()));
            rotinaInsumo.setRotina(rotina);
            rotinaInsumo.setUnidadeMedida(atual.getUnidadeMedida());
            rotinas.add(rotinaInsumo);
            rotina.getRotinaInsumos().add(rotinaInsumo);
        }

        return rotinaInsumoRepository.saveAll(rotinas);
    }

    @Transactional
    public void deletar(Integer id) {
        if (!rotinaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));
        }

        auditService.registrar(getUsuarioLogado(), "DELECAO", "ROTINA", "Deletou rotina ID: " + id);
        rotinaInsumoRepository.deleteByRotinaId(id);
        rotinaRepository.deleteById(id);
    }

    @Transactional
    public void darBaixa(Integer id) {

        if (!rotinaRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não foi encontrada uma rotina com este id %d", id)
            );
        }

        List<RotinaInsumo> relacoes =
                rotinaInsumoRepository.findAllByRotinaId(id);

        for (RotinaInsumo relacao : relacoes) {

            double restante = relacao.getQuantidadeInsumo();

            List<Lote> lotesDisponiveis =
                    loteService.lotePorInsumoId(
                            relacao.getInsumo().getIdInsumo()
                    );

            List<Lote> lotesValidos = lotesDisponiveis.stream()
                    .filter(l -> l.getUnidadeMedida()
                            .equalsIgnoreCase(relacao.getUnidadeMedida()))
                    .toList();

            double estoqueTotal = 0.0;

            for (Lote lote : lotesValidos) {

                if (!lote.getUnidadeMedida().equalsIgnoreCase(relacao.getUnidadeMedida())) continue;

                estoqueTotal += lote.getQuantidadeAtual()
                        * lote.getQuantidadeMedida();
            }

            if (estoqueTotal < restante) {
                throw new EntradaInvalidaException(
                        "Estoque insuficiente para: "
                                + relacao.getInsumo().getNome()
                );
            }

            for (Lote lote : lotesValidos) {

                if (restante <= 0) break;

                int pacotesDisponiveis = lote.getQuantidadeAtual();
                double porPacote = lote.getQuantidadeMedida();

                for (int i = 0; i < pacotesDisponiveis && restante > 0; i++) {
                    restante -= porPacote;
                    loteService.removerQuantidade(
                            lote.getIdLote(),
                            1
                    );
                }
            }
        }

        auditService.registrar(
                getUsuarioLogado(),
                "BAIXA",
                "ROTINA",
                "Deu baixa no estoque pela rotina ID: " + id
        );
    }

    public Rotina atualizar(RotinaRequestDto rotina, Integer id) {
        Optional<Rotina> rotinaExiste = rotinaRepository.findById(id);
        if (rotina == null) {
            throw new EntradaInvalidaException("A rotina não pode ser nula!");
        }
        if (rotinaExiste.isEmpty()) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrada uma rotina com este id %d", id));
        }

        Rotina rotinaParaAtualizar = RotinaMapper.toEntity(rotina);
        Rotina salva = rotinaRepository.save(rotinaParaAtualizar);
        auditService.registrar(getUsuarioLogado(), "ATUALIZACAO", "ROTINA", "Atualizou rotina ID: " + id);
        return salva;
    }

    private String getUsuarioLogado() {
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }

    public PreviewBaixaResponseDto mostrarPreview(Integer rotinaId) {

        Rotina rotina = rotinaRepository.findById(rotinaId)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        "Rotina não encontrada"));

        List<RotinaInsumo> relacoes =
                rotinaInsumoRepository.findAllByRotinaId(rotinaId);

        PreviewBaixaResponseDto response = new PreviewBaixaResponseDto();
        response.setRotinaId(rotina.getId());
        response.setTitulo(rotina.getTitulo());

        Map<Integer, Integer> necessidadePorInsumo = new HashMap<>();
        Map<Integer, String> nomePorInsumo = new HashMap<>();

        for (RotinaInsumo relacao : relacoes) {

            Integer insumoId = relacao.getInsumo().getIdInsumo();
            Integer qtd = Math.abs(relacao.getQuantidadeInsumo());

            necessidadePorInsumo.merge(insumoId, qtd, Integer::sum);
            nomePorInsumo.putIfAbsent(insumoId, relacao.getInsumo().getNome());
        }

        List<ItemBaixaResponseDto> itens = new ArrayList<>();

        for (var entry : necessidadePorInsumo.entrySet()) {

            Integer insumoId = entry.getKey();
            Double qtdNecessaria = Double.valueOf(entry.getValue());
            Double qtdOriginal = qtdNecessaria;
            Integer retiradas = 0;
            List<Lote> lotesDisponiveis =
                    loteService.lotePorInsumoId(insumoId);

            List<LoteConsumidoResponseDto> lotesConsumidos = new ArrayList<>();

            String unidadeBase = null;

            for (Lote lote : lotesDisponiveis) {

                if (qtdNecessaria <= 0) break;

                if (unidadeBase == null) {
                    unidadeBase = lote.getUnidadeMedida();
                } else if (!unidadeBase.equals(lote.getUnidadeMedida())) {
                    continue;
                }

                int disponivel = lote.getQuantidadeAtual();

                if (disponivel <= 0) continue;

                for (int i = 0; i < disponivel && qtdNecessaria > 0; i++) {
                    qtdNecessaria -= lote.getQuantidadeMedida();
                    retiradas++;
                }

                LoteConsumidoResponseDto dto = new LoteConsumidoResponseDto();
                dto.setLoteId(lote.getIdLote());
                dto.setMarca(lote.getMarca().getNomeMarca());
                dto.setValidade(lote.getDataValidade());
                dto.setQuantidadeConsumida(lote.getQuantidadeAtual());
                dto.setUnidadeMedida(lote.getUnidadeMedida());

                lotesConsumidos.add(dto);
            }

            ItemBaixaResponseDto item = new ItemBaixaResponseDto();
            item.setInsumoId(insumoId);
            item.setNomeInsumo(nomePorInsumo.get(insumoId));
            item.setQuantidadeNecessaria(retiradas);
            item.setUnidadeMedida(unidadeBase);
            item.setLotes(lotesConsumidos);

            itens.add(item);
        }

        response.setItens(itens);
        return response;
    }
}
