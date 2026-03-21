package com.toomate.backend.dto.estoque_grupo;

import com.toomate.backend.enums.StatusVencimento;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class EstoqueMapper {
    public static VencimentoView toView(EstoqueVencimento estoqueVencimento){
        if (estoqueVencimento == null){
            return null;
        }

        VencimentoView view = new VencimentoView();

        LocalDate dtVencimento = estoqueVencimento.getDataValidade();
        Long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), dtVencimento);
        String status = calcularStatus(diasRestantes).getLabel();

        view.setId(estoqueVencimento.getIdLote());
        view.setInsumo(estoqueVencimento.getNome());
        view.setMarca(estoqueVencimento.getNomeMarca());
        view.setEstoqueAtual(String.format("%.2f%s", estoqueVencimento.getQuantidadeMedida(), estoqueVencimento.getUnidadeMedida()));
        view.setDtVencimento(estoqueVencimento.getDataValidade());
        view.setDiasRestantes(diasRestantes);
        view.setStatus(status);

        return view;
    }

    public static List<VencimentoView> toView(List<EstoqueVencimento> estoques){
        return estoques.stream().map(atual -> toView(atual)).toList();
    }

    private static StatusVencimento calcularStatus(Long diasRestantes){
        if(diasRestantes < 0){
            return StatusVencimento.VENCIDO;
        }

        if (diasRestantes <= 7){
            return StatusVencimento.VENCE_LOGO;
        }

        return StatusVencimento.NO_PRAZO;
    }
}
