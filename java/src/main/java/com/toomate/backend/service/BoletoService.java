package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.mapper.boleto.BoletoMapper;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.repository.BoletoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final AuditService auditService;

    public BoletoService(BoletoRepository boletoRepository, AuditService auditService) {
        this.boletoRepository = boletoRepository;
        this.auditService = auditService;
    }

    public Boleto cadastrar(BoletoRequestDto request) {
        Boleto boleto = BoletoMapper.toEntity(request);
        boletoRepository.save(boleto);
        auditService.registrar(getUsuarioLogado(), "CADASTRO", "BOLETO", "Cadastrou boleto: " + boleto.getDescricao());
        return boleto;
    }

    public List<Boleto> listarBoletos() {
        return boletoRepository.findAll();
    }

    public Page<Boleto> listarBoletosPaginado(Integer pagina, Integer tamanho) {
        Pageable pageable = PageRequest.of(Math.max(pagina, 0), Math.max(tamanho, 1));
        return boletoRepository.findAll(pageable);
    }

    public Boleto buscarPorId(Integer id) {

        Boleto boleto = boletoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("O boleto não foi encontrado!"));
        return boleto;
    }

    public Boleto editar(Integer idBoleto, Boleto boleto) {

        boleto.setIdBoleto(idBoleto);

        if (boletoRepository.existsById(idBoleto)) {
            Boleto save = boletoRepository.save(boleto);
            auditService.registrar(getUsuarioLogado(), "ATUALIZACAO", "BOLETO", "Editou boleto ID: " + idBoleto);
            return save;
        }

        throw new EntidadeNaoEncontradaException("O boleto não foi encontrado!");
    }

    public void deletarPorId(Integer idBoleto) {

        if (!boletoRepository.existsById(idBoleto)) {
            throw new EntidadeNaoEncontradaException("O boleto não foi encontrado!");
        }
        auditService.registrar(getUsuarioLogado(), "DELECAO", "BOLETO", "Deletou boleto ID: " + idBoleto);
        boletoRepository.deleteById(idBoleto);
    }

    public List<Boleto> buscarPorCategoria(String categoria) {

        List<Boleto> boletosEncontrados = boletoRepository.findByCategoriaContainingIgnoreCase(categoria);
            return boletosEncontrados;
    }

    public List<Boleto> buscarPorFornecedor(Integer idFornecedor) {

        List<Boleto> boletosEncontrados = boletoRepository.findByIdFornecedor(idFornecedor);
            return boletosEncontrados;

    }

    public Boolean existePorId(Integer id){
        return boletoRepository.existsById(id);
    }


    public List<String> listarCategorias() {
        return boletoRepository.listarCategorias();
    }

    private String getUsuarioLogado() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
