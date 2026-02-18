package com.toomate.backend.service;

import com.toomate.backend.dto.estoque_grupo.EstoqueGeral;
import com.toomate.backend.dto.estoque_grupo.EstoqueGrupo;
import com.toomate.backend.dto.estoque_grupo.InsumoAgrupado;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.integration.EnviarNotificacao;
import com.toomate.backend.model.*;
import com.toomate.backend.observer.LoteListener;
import com.toomate.backend.repository.LoteRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoteService implements LoteListener {
    private final LoteRepository loteRepository;
    private final EnviarNotificacao enviarNotificacao;


    public LoteService(LoteRepository loteRepository, EnviarNotificacao enviarNotificacao) {
        this.loteRepository = loteRepository;
        this.enviarNotificacao = enviarNotificacao;
    }

    @Override
    public void notificarMudanca(Insumo insumo) {
        Double total = loteRepository.getEstoqueInsumo(insumo.getIdInsumo());
        if (total < insumo.getQtdMinima()) {
            enviarNotificacao.enviarNotif(insumo, total);
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
        return lote;
    }

    public void deletar(Integer id) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        loteRepository.deleteById(id);
        notificarMudanca(loteRepository.findById(id).get().getMarca().getInsumo());
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

        Lote lote = loteRepository.findById(id).get();
        if (lote.getQuantidadeMedida() - quantidadeMedida < 0) {
            throw new EntradaInvalidaException("Quantidade medida não pode ser negativa");
        }

        lote.removerQuantidadeMedida(quantidadeMedida);
        loteRepository.save(lote);
        notificarMudanca(lote.getMarca().getInsumo());

    }

    public void adicionarQuantidade(Integer id, Double quantidadeMedida) {
        if (!loteRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(String.format("Não foi encontrado lote com o id %d", id));
        }

        Lote lote = loteRepository.findById(id).get();
        lote.adicionarQuantidadeMedida(quantidadeMedida);
        loteRepository.save(lote);
        notificarMudanca(lote.getMarca().getInsumo());

    }

    public List<EstoqueGrupo> getEstoque() {
        Map<Integer, EstoqueGrupo> mapa = new LinkedHashMap<>();
        List<EstoqueGeral> estoque = loteRepository.buscarEstoque();

        for (EstoqueGeral item : estoque) {
            Integer fkInsumo = item.getIdInsumo();
            String nomeCategoria = item.getNomeCategoria();

            if (!mapa.containsKey(fkInsumo)) {
                EstoqueGrupo grupo = new EstoqueGrupo();

                grupo.setFkInsumo(fkInsumo);
                grupo.setFkCategoria(item.getIdCategoria());
                grupo.setCategoria(nomeCategoria);
                grupo.setInsumo(item.getNomeInsumo());
                grupo.setMedida(item.getUnidadeMedida());
                grupo.setItens(new ArrayList<>());

                mapa.put(fkInsumo, grupo);
            }
            mapa.get(fkInsumo).getItens().add(new InsumoAgrupado(item.getIdInsumo(), item.getNomeMarca(), item.getQuantidadeMedida(), item.getUnidadeMedida(), item.getDataValidade()));
            mapa.get(fkInsumo).calcularQtdTotal();
            mapa.get(fkInsumo).calcularMenorData();
        }

        return new ArrayList<>(mapa.values());
    }
}
