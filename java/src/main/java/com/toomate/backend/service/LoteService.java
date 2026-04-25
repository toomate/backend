package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.Kpi;
import com.toomate.backend.dto.estoque_grupo.*;
import com.toomate.backend.dto.lote.LotePatchDto;
import com.toomate.backend.enums.StatusVencimento;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.*;
import com.toomate.backend.observer.LoteListener;
import com.toomate.backend.repository.LoteRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoteService implements LoteListener {
    private final LoteRepository loteRepository;
    private final ProducerRabbitMQ producerRabbitMQ;
    private final AuditService auditService;

    public LoteService(LoteRepository loteRepository, ProducerRabbitMQ producerRabbitMQ, AuditService auditService) {
        this.loteRepository = loteRepository;
        this.producerRabbitMQ = producerRabbitMQ;
        this.auditService = auditService;
    }

    @Override
    public void notificarMudanca(Insumo insumo) {
        Double total = loteRepository.getEstoqueInsumo(insumo.getIdInsumo());
        if (total < insumo.getQtdMinima()) {
            producerRabbitMQ.enviarNotif(insumo, total);
        }
    }

    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public Lote listarPorId(Integer id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado lote com o id %d", id)));
    }

    public Lote cadastrar(Lote lote) {
        String usuarioLogado = getUsuarioLogado();
        if (lote == null) {
            throw new EntradaInvalidaException("O lote não pode ser nulo!");
        }

        if (lote.getUsuario() == null) {
            throw new EntradaInvalidaException("O fornecedor não pode ser nulo!");
        }

        if (lote.getMarca() == null) {
            throw new EntradaInvalidaException("O insumo não pode ser nulo!");
        }

        if (lote.getQuantidadeMedida() <= 0) {
            throw new EntradaInvalidaException("A quantidade não pode ser igual ou menor que zero.");
        }

        if (lote.getPrecoUnitario() <= 0) {
            throw new EntradaInvalidaException("A quantidade não pode ser igual ou menor que zero.");
        }

        if (lote.getPrecoUnitario() >= 999) {
            throw new EntradaInvalidaException("A quantidade não pode ser maior ou igual a 999,99R$.");
        }

        lote = loteRepository.save(lote);
        notificarMudanca(lote.getMarca().getInsumo());
        log.info("Usuário {} cadastrou um novo lote da marca: {} com {} {}", usuarioLogado, lote.getMarca().getNomeMarca(), lote.getQuantidadeMedida(), lote.getMarca().getInsumo().getUnidadeMedida());
        auditService.registrar(usuarioLogado, "CADASTRO", "LOTE",
                String.format("Cadastrou lote da marca %s com %.2f %s", lote.getMarca().getNomeMarca(), lote.getQuantidadeMedida(), lote.getMarca().getInsumo().getUnidadeMedida()));
        return lote;
    }

    public void deletar(Integer id) {
        String usuarioLogado = getUsuarioLogado();
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        // fetch the lote before deleting so we can notify based on its insumo
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id)));

        loteRepository.deleteById(id);
        log.info("Usuário {} deletou o Lote com ID: {}", usuarioLogado, id);
        auditService.registrar(usuarioLogado, "DELECAO", "LOTE", "Deletou lote ID: " + id);
        if (lote.getMarca() != null && lote.getMarca().getInsumo() != null) {
            notificarMudanca(lote.getMarca().getInsumo());
        }
    }

    public Lote atualizar(Integer id, Lote lote) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        lote.setIdLote(id);
        lote = loteRepository.save(lote);
        notificarMudanca(lote.getMarca().getInsumo());
        return lote;
    }

    public Boolean existePorId(Integer id) {
        return loteRepository.existsById(id);
    }

    public Lote lotePorId(Integer id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado nenhuma marca com o id %d", id)));
    }

    public void removerQuantidade(Integer id, Double quantidadeMedida) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id)));
        if (lote.getQuantidadeMedida() - quantidadeMedida < 0) {
            throw new EntradaInvalidaException("Quantidade medida não pode ser negativa");
        }

        lote.removerQuantidadeMedida(quantidadeMedida);
        loteRepository.save(lote);
        if (lote.getMarca() != null && lote.getMarca().getInsumo() != null) {
            notificarMudanca(lote.getMarca().getInsumo());
        }

    }

    public void adicionarQuantidade(Integer id, Double quantidadeMedida) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id)));
        lote.adicionarQuantidadeMedida(quantidadeMedida);
        loteRepository.save(lote);
        if (lote.getMarca() != null && lote.getMarca().getInsumo() != null) {
            notificarMudanca(lote.getMarca().getInsumo());
        }
    }

    public List<EstoqueGrupo> buscarEstoque() {
        List<EstoqueGeral> estoque = loteRepository.buscarEstoque();

        Map<Integer, EstoqueGrupo> estoqueResponse = estruturarJson(estoque);

        return new ArrayList<>(estoqueResponse.values());
    }

    public List<EstoqueGrupo> buscarEstoquePorCategoria(String categoria) {
        List<EstoqueGeral> estoque = loteRepository.buscarEstoquePorCategoria(categoria);

        Map<Integer, EstoqueGrupo> estoqueResponse = estruturarJson(estoque);

        return new ArrayList<>(estoqueResponse.values());
    }

    public List<EstoqueGrupo> pesquisarEstoquePorInsumo(String insumo) {
        List<EstoqueGeral> estoque = loteRepository.pesquisarEstoquePorInsumo(insumo);

        Map<Integer, EstoqueGrupo> estoqueResponse = estruturarJson(estoque);

        return new ArrayList<>(estoqueResponse.values());
    }

    public List<EstoqueVencimento> buscarEstoqueVencimento() {
        return loteRepository.buscarEstoqueVencimento();
    }

    public List<Kpi> buscarKpisVencimentos() {
        List<EstoqueVencimento> estoqueVencimentos = loteRepository.buscarEstoqueVencimento();

        List<VencimentoView> vencimentoViews = EstoqueMapper.toView(estoqueVencimentos);

        long vencidos = 0;
        long vencemLogo = 0;
        long vencemHoje = 0;

        LocalDate hoje = LocalDate.now();

        for (VencimentoView insumo : vencimentoViews) {

            if (StatusVencimento.VENCIDO.getLabel().equals(insumo.getStatus())) {
                vencidos++;
            }

            if (StatusVencimento.VENCE_LOGO.getLabel().equals(insumo.getStatus())) {
                vencemLogo++;
            }

            if (insumo.getDtVencimento().isEqual(hoje)) {
                vencemHoje++;
            }
        }

        return List.of(
                new Kpi("expirado", "Insumos Vencidos", (double) vencidos),
                new Kpi("vencemHoje", "Vencem Hoje", (double) vencemHoje),
                new Kpi("prox7dias", "Próximos 7 Dias", (double) vencemLogo)
        );
    }


    private Map<Integer, EstoqueGrupo> estruturarJson(List<EstoqueGeral> estoque) {
        Map<Integer, EstoqueGrupo> mapa = new LinkedHashMap<>();
        for (EstoqueGeral item : estoque) {
            Integer fkInsumo = item.getIdInsumo();
            String nomeCategoria = item.getNomeCategoria();

            if (!mapa.containsKey(fkInsumo)) {
                EstoqueGrupo grupo = new EstoqueGrupo();

                grupo.setFkInsumo(fkInsumo);
                grupo.setFkCategoria(item.getIdCategoria());
                grupo.setCategoria(nomeCategoria);
                grupo.setInsumo(item.getNomeInsumo());
                grupo.setQtdMinima(item.getQtdMinima());
                grupo.setMedida(item.getUnidadeMedida());
                grupo.setItens(new ArrayList<>());

                mapa.put(fkInsumo, grupo);
            }
            mapa.get(fkInsumo).getItens().add(new InsumoAgrupado(item.getIdInsumo(), item.getIdMarca(), item.getNomeMarca(), item.getIdLote(), item.getQuantidadeMedida(), item.getQtdMinima(), item.getUnidadeMedida(), item.getDataValidade()));
            mapa.get(fkInsumo).calcularQtdTotal();
            mapa.get(fkInsumo).calcularMenorData();
        }

        return mapa;
    }

    @Transactional
    public void atualizarQuantidades(List<LotePatchDto> request) {
        String usuarioLogado = getUsuarioLogado();
        List<Integer> ids = request.stream()
                .map(LotePatchDto::getId)
                .toList();

        List<Lote> lotes = loteRepository.findAllById(ids);

        Map<Integer, Lote> mapa = lotes.stream()
                .collect(Collectors.toMap(Lote::getIdLote, e -> e));

        List<Insumo> alterados = lotes.stream()
                .map(Lote::getMarca)
                .filter(Objects::nonNull)
                .map(Marca::getInsumo)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        for (LotePatchDto dto : request) {
            Lote lote = mapa.get(dto.getId());

            if (lote == null) {
                throw new EntidadeNaoEncontradaException("Não foi encontrado um lote com o id: " + dto.getId());
            }
            log.info("Usuário {} atualizou a quantidade do lote: {} às {}", usuarioLogado, dto.getId(), LocalDateTime.now());
            auditService.registrar(usuarioLogado, "ATUALIZACAO", "LOTE", "Atualizou quantidade do lote ID: " + dto.getId());

            lote.setQuantidadeMedida(dto.getQuantidadeMedida());
        }

        // notify any related insumos whose total stock might have changed
        for (Insumo insumo : alterados) {
            if (insumo != null) {
                notificarMudanca(insumo);
            }
        }
    }

    public List<Lote> lotePorInsumoId(Integer id) {
        return loteRepository.lotePorIdInsumo(id);
    }


    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
