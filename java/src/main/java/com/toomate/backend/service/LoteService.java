package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.Kpi;
import com.toomate.backend.dto.estoque_grupo.*;
import com.toomate.backend.dto.lote.LotePatchDto;
import com.toomate.backend.dto.lote.ResumoLotesPeriodoDto;
import com.toomate.backend.dto.notification.NotificationDto;
import com.toomate.backend.dto.page.PageResponseDto;
import com.toomate.backend.enums.StatusVencimento;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.ProducerRabbitMQ;
import com.toomate.backend.model.*;
import com.toomate.backend.observer.LoteListener;
import com.toomate.backend.repository.HistoricoLoteRepository;
import com.toomate.backend.repository.InsumoRepository;
import com.toomate.backend.repository.LoteRepository;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@Cacheable(cacheNames = "vencimentos", key = "{#pagina, #tamanho}")
public class LoteService implements LoteListener {
    private final LoteRepository loteRepository;
    private final InsumoRepository insumoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProducerRabbitMQ producerRabbitMQ;
    private final AuditService auditService;
    private final HistoricoLoteRepository historicoLoteRepository;

    public LoteService(LoteRepository loteRepository, InsumoRepository insumoRepository, UsuarioRepository usuarioRepository, ProducerRabbitMQ producerRabbitMQ, AuditService auditService, HistoricoLoteRepository historicoLoteRepository) {
        this.loteRepository = loteRepository;
        this.insumoRepository = insumoRepository;
        this.usuarioRepository = usuarioRepository;
        this.producerRabbitMQ = producerRabbitMQ;
        this.auditService = auditService;
        this.historicoLoteRepository = historicoLoteRepository;
    }

    @Override
    public void notificarMudanca(Insumo insumo) {
        log.info("MUDANÇA NO INSUMO " + insumo.getNome());
        Double total = loteRepository.getEstoqueInsumo(insumo.getIdInsumo());
        log.info("QUANTIDADE TOTAL %f MINIMA %d".formatted(total, insumo.getQtdMinima()));
        if (total < insumo.getQtdMinima()) {
            producerRabbitMQ.enviarNotif(new NotificationDto(insumo, total));
        }
    }

    @Cacheable(cacheNames = "lote", key = "'todos'")
    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public Page<Lote> listarPaginado(Pageable pageable) {
        return loteRepository.findAll(pageable);
    }

    public Page<Lote> listarPaginadoPorPeriodo(LocalDate dataInicial, LocalDate dataFinal, Pageable pageable) {
        if (dataInicial == null || dataFinal == null) {
            return loteRepository.findAll(pageable);
        }
        return loteRepository.findByDataEntradaBetween(dataInicial, dataFinal, pageable);
    }

    public ResumoLotesPeriodoDto resumoPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        if (dataInicial == null || dataFinal == null) {
            Double total = Optional.ofNullable(loteRepository.somarValorTotal()).orElse(0.0);
            long registros = loteRepository.count();
            return new ResumoLotesPeriodoDto(total, registros);
        }

        Double total = Optional.ofNullable(loteRepository.somarValorPorPeriodo(dataInicial, dataFinal)).orElse(0.0);
        long registros = loteRepository.countByDataEntradaBetween(dataInicial, dataFinal);
        return new ResumoLotesPeriodoDto(total, registros);
    }

    public Lote listarPorId(Integer id) {
        return loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(
                        String.format("Não foi encontrado lote com o id %d", id)));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    @Transactional
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

        Insumo insumo = insumoRepository.findById(
                lote.getMarca().getInsumo().getIdInsumo()
        ).orElseThrow();

        insumo.setAtivo(true);

        lote = loteRepository.save(lote);
        notificarMudanca(lote.getMarca().getInsumo());
        log.info("Usuário '{}' cadastrou lote ID {} | Marca: {} | Insumo: {} | Qtd original: {} | Medida: {} {} | Preço unit.: R${} | Validade: {}",
                usuarioLogado, lote.getIdLote(), lote.getMarca().getNomeMarca(),
                lote.getMarca().getInsumo().getNome(), lote.getQuantidadeOriginal(),
                lote.getQuantidadeMedida(), lote.getUnidadeMedida(),
                lote.getPrecoUnitario(), lote.getDataValidade());
        auditService.registrar(usuarioLogado, "CADASTRO", "LOTE", lote.getIdLote(),
                String.format("Cadastrou lote da marca '%s' (insumo: %s) | %d un x %.2f %s a R$%.2f/un | validade: %s",
                        lote.getMarca().getNomeMarca(), lote.getMarca().getInsumo().getNome(),
                        lote.getQuantidadeOriginal(), lote.getQuantidadeMedida(), lote.getUnidadeMedida(),
                        lote.getPrecoUnitario(), lote.getDataValidade()),
                null,
                Map.of("marca", lote.getMarca().getNomeMarca(),
                        "insumo", lote.getMarca().getInsumo().getNome(),
                        "quantidadeOriginal", lote.getQuantidadeOriginal(),
                        "quantidadeAtual", lote.getQuantidadeAtual(),
                        "quantidadeMedida", lote.getQuantidadeMedida(),
                        "unidade", lote.getUnidadeMedida(),
                        "precoUnitario", lote.getPrecoUnitario(),
                        "dataValidade", String.valueOf(lote.getDataValidade())));
        return lote;
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    public void deletar(Integer id) {
        String usuarioLogado = getUsuarioLogado();
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        // fetch the lote before deleting so we can notify based on its insumo
        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id)));

        loteRepository.deleteById(id);
        log.info("Usuário '{}' deletou lote ID {} | Marca: {} | Insumo: {} | Qtd atual: {}/{} | Medida: {} {}",
                usuarioLogado, id, lote.getMarca().getNomeMarca(),
                lote.getMarca().getInsumo().getNome(), lote.getQuantidadeAtual(),
                lote.getQuantidadeOriginal(), lote.getQuantidadeMedida(), lote.getUnidadeMedida());
        auditService.registrar(usuarioLogado, "DELECAO", "LOTE", id,
                String.format("Deletou lote da marca '%s' (insumo: %s) | %d/%d un x %.2f %s",
                        lote.getMarca().getNomeMarca(), lote.getMarca().getInsumo().getNome(),
                        lote.getQuantidadeAtual(), lote.getQuantidadeOriginal(),
                        lote.getQuantidadeMedida(), lote.getUnidadeMedida()),
                Map.of("marca", lote.getMarca().getNomeMarca(),
                        "insumo", lote.getMarca().getInsumo().getNome(),
                        "quantidadeOriginal", lote.getQuantidadeOriginal(),
                        "quantidadeAtual", lote.getQuantidadeAtual(),
                        "quantidadeMedida", lote.getQuantidadeMedida(),
                        "unidade", lote.getUnidadeMedida()),
                null);
        if (lote.getMarca() != null && lote.getMarca().getInsumo() != null) {
            notificarMudanca(lote.getMarca().getInsumo());
        }
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
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

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    public void removerQuantidade(Integer id, Integer quantidadeMedida) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        Lote lote = loteRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id)));
        if (lote.getQuantidadeAtual() - quantidadeMedida < 0) {
            throw new EntradaInvalidaException("Quantidade medida não pode ser negativa");
        }

        lote.removerQuantidadeMedida(quantidadeMedida);
        if (lote.getQuantidadeAtual() == 0) lote.setAtivo(false);
        loteRepository.save(lote);
        if (lote.getMarca() != null && lote.getMarca().getInsumo() != null) {
            notificarMudanca(lote.getMarca().getInsumo());
        }

    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    public void adicionarQuantidade(Integer id, Integer quantidadeMedida) {
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

    @Cacheable(cacheNames = "estoque", key = "{#pagina, #tamanho}")
    public PageResponseDto<EstoqueGrupo> buscarEstoque(Integer pagina, Integer tamanho) {
        List<EstoqueGeral> tudo = loteRepository.buscarEstoque();

        List<EstoqueGrupo> agrupado = estruturarJson(tudo);

        int inicio = pagina * tamanho;
        int fim = Math.min(inicio + tamanho, agrupado.size());

        List<EstoqueGrupo> pageContent = agrupado.subList(inicio, fim);
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);

        Page<EstoqueGrupo> response = new PageImpl<>(pageContent, pgRequest, agrupado.size());

        return new PageResponseDto<EstoqueGrupo>().de(response);
    }

    public PageResponseDto<EstoqueGrupo> buscarEstoquePorCategoria(String categoria, Integer pagina, Integer tamanho) {
        List<EstoqueGeral> estoque = loteRepository.buscarEstoquePorCategoria(categoria);

        List<EstoqueGrupo> agrupado = estruturarJson(estoque);

        int inicio = pagina * tamanho;
        int fim = Math.min(inicio + tamanho, agrupado.size());

        List<EstoqueGrupo> pageContent = agrupado.subList(inicio, fim);
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);

        Page<EstoqueGrupo> response = new PageImpl<>(pageContent, pgRequest, agrupado.size());

        return new PageResponseDto<EstoqueGrupo>().de(response);
    }

    public PageResponseDto<EstoqueGrupo> pesquisarEstoquePorInsumo(String insumo, Integer pagina, Integer tamanho) {
        List<EstoqueGeral> estoque = loteRepository.pesquisarEstoquePorInsumo(insumo);

        List<EstoqueGrupo> agrupado = estruturarJson(estoque);

        int inicio = pagina * tamanho;
        int fim = Math.min(inicio + tamanho, agrupado.size());

        List<EstoqueGrupo> pageContent = agrupado.subList(inicio, fim);
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);

        Page<EstoqueGrupo> response = new PageImpl<>(pageContent, pgRequest, agrupado.size());

        return new PageResponseDto<EstoqueGrupo>().de(response);
    }

    @Cacheable(cacheNames = "vencimentos", key = "'todos'")
    public List<EstoqueVencimento> buscarEstoqueVencimento() {
        return loteRepository.buscarEstoqueVencimento();
    }

    public PageResponseDto<VencimentoView> buscarEstoqueVencimentoPaginado(Integer pagina, Integer tamanho) {
        List<EstoqueVencimento> vencimentos = loteRepository.buscarEstoqueVencimento();
        List<VencimentoView> vencimentoViews = EstoqueMapper.toView(vencimentos);

        int inicio = pagina * tamanho;
        int fim = Math.min(inicio + tamanho, vencimentoViews.size());

        List<VencimentoView> pageContent = vencimentoViews.subList(inicio, fim);
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);

        Page<VencimentoView> response = new PageImpl<>(pageContent, pgRequest, vencimentoViews.size());

        return new PageResponseDto<VencimentoView>().de(response);
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


    private List<EstoqueGrupo> estruturarJson(List<EstoqueGeral> estoque) {
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
                grupo.setQtdAtual(item.getQuantidadeMedida());
                grupo.setItens(new ArrayList<>());

                mapa.put(fkInsumo, grupo);
            }

            mapa.get(fkInsumo).getItens().add(new InsumoAgrupado(item.getIdInsumo(), item.getIdMarca(), item.getNomeMarca(), item.getIdLote(), item.getQuantidadeMedida(), item.getQuantidadeTotal(), item.getQtdMinima(), item.getUnidadeMedida(), item.getDataValidade()));
            mapa.get(fkInsumo).calcularQtdTotal();
            mapa.get(fkInsumo).calcularQtdAtual();
            mapa.get(fkInsumo).calcularMenorData();
        }

        for (EstoqueGrupo grupo : mapa.values()) {
            grupo.getItens().removeIf(lote -> lote.getQuantidadeTotal() <= 0);
        }
        List<EstoqueGrupo> lista = new ArrayList<>(mapa.values());

        lista.sort(
                Comparator
                        .comparing((EstoqueGrupo grupo) -> grupo.getQtdTotal() == 0)
                        .thenComparing((EstoqueGrupo grupo) -> grupo.getQtdAtual() < grupo.getQtdMinima())
                        .reversed()
                        .thenComparing((EstoqueGrupo grupo) -> ChronoUnit.DAYS.between(LocalDate.now(), grupo.getDtVencimento()) <= 7, Comparator.reverseOrder())
                        .thenComparing(EstoqueGrupo::getQtdAtual)
                        .thenComparing(EstoqueGrupo::getDtVencimento)
        );

        return lista;
    }

    private void guardarHistorico(Lote lote, Integer quantidadeRetirada) {
        HistoricoLote historico = new HistoricoLote();
        historico.setLote(lote);
        historico.setQuantidadeRetirada(quantidadeRetirada);
        historico.setDataHoraAlteracao(LocalDateTime.now());

        historicoLoteRepository.save(historico);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "estoque", allEntries = true),
            @CacheEvict(cacheNames = "lote", allEntries = true),
            @CacheEvict(cacheNames = "vencimentos", allEntries = true)

    })
    @Transactional
    public void atualizarQuantidades(List<LotePatchDto> request) {
        String usuarioLogado = getUsuarioLogado();
        List<Integer> ids = request.stream()
                .map(LotePatchDto::getId)
                .toList();

        List<Lote> lotes = loteRepository.findAllById(ids);

        Map<Integer, Lote> mapa = lotes.stream()
                .collect(Collectors.toMap(Lote::getIdLote, e -> e));

        for (LotePatchDto dto : request) {
            Lote lote = mapa.get(dto.getId());

            if (lote == null) {
                throw new EntidadeNaoEncontradaException("Não foi encontrado um lote com o id: " + dto.getId());
            }
            log.info("Usuário '{}' atualizou quantidade do lote ID {} | Marca: {} | De: {} → Para: {}",
                    usuarioLogado, dto.getId(),
                    lote.getMarca() != null ? lote.getMarca().getNomeMarca() : "N/A",
                    lote.getQuantidadeAtual(), dto.getQuantidadeTotal());
            auditService.registrar(usuarioLogado, "ATUALIZACAO", "LOTE", dto.getId(),
                    String.format("Atualizou quantidade do lote (marca: %s) de %d para %d",
                            lote.getMarca() != null ? lote.getMarca().getNomeMarca() : "N/A",
                            lote.getQuantidadeAtual(), dto.getQuantidadeTotal()),
                    Map.of("quantidadeAtual", lote.getQuantidadeAtual()),
                    Map.of("quantidadeAtual", dto.getQuantidadeTotal()));

            guardarHistorico(lote, lote.getQuantidadeAtual() - dto.getQuantidadeTotal());

            lote.setQuantidadeAtual(dto.getQuantidadeTotal());
        }

        List<Insumo> alterados = lotes.stream()
                .map(Lote::getMarca)
                .filter(Objects::nonNull)
                .map(Marca::getInsumo)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        alterados.forEach(this::notificarMudanca);

    }

    public List<Lote> lotePorInsumoId(Integer id) {
        return loteRepository.lotePorIdInsumo(id);
    }


    private String getUsuarioLogado() {
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }

    public List<NotificationDto> buscarPorDias(int dias) {
        List<Lote> lotes = loteRepository.buscarLotePorDia(dias);

        return lotes.stream()
                .map(l -> new NotificationDto(l,l.getMarca().getInsumo().getNome()))
                .toList();

    }
}
