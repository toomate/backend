package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.dto.boleto.BoletoRequestDto;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.mapper.boleto.BoletoMapper;
import com.toomate.backend.model.Boleto;
import com.toomate.backend.repository.BoletoRepository;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final AuditService auditService;
    private final UsuarioRepository usuarioRepository;

    public BoletoService(BoletoRepository boletoRepository, AuditService auditService, UsuarioRepository usuarioRepository) {
        this.boletoRepository = boletoRepository;
        this.auditService = auditService;
        this.usuarioRepository = usuarioRepository;
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

        return boletoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("O boleto não foi encontrado!"));
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

        return boletoRepository.findByCategoriaContainingIgnoreCase(categoria);
    }

    public List<Boleto> buscarPorFornecedor(Integer idFornecedor) {

        return boletoRepository.findByIdFornecedor(idFornecedor);

    }

    public Boolean existePorId(Integer id){
        return boletoRepository.existsById(id);
    }


    public List<String> listarCategorias() {
        return boletoRepository.listarCategorias();
    }

    private String getUsuarioLogado() {
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }

    public List<Boleto> buscarPorDias(int dias){
        return boletoRepository.buscarBoletosPorDias(dias);
    }
}
