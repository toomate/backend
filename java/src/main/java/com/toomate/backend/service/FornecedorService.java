package com.toomate.backend.service;

import com.toomate.backend.audit.AuditService;
import com.toomate.backend.exceptions.EntidadeNaoEncontradaException;
import com.toomate.backend.exceptions.EntradaInvalidaException;
import com.toomate.backend.exceptions.RecursoExisteException;
import com.toomate.backend.model.Fornecedor;
import com.toomate.backend.repository.FornecedorRepository;
import com.toomate.backend.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FornecedorService {
    private final FornecedorRepository fornecedorRepository;
    private final UsuarioRepository usuarioRepository;
    private final AuditService auditService;

    public FornecedorService(FornecedorRepository fornecedorRepository, UsuarioRepository usuarioRepository, AuditService auditService) {
        this.fornecedorRepository = fornecedorRepository;
        this.usuarioRepository = usuarioRepository;
        this.auditService = auditService;
    }

    public List<Fornecedor> listar() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor retornarPeloId(Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Nao foi encontrado um fornecedor com o id %d", id))
        );
    }

    public List<Fornecedor> filtrar(String razaoSocial) {
        if (razaoSocial == null || razaoSocial.isBlank()) {
            return fornecedorRepository.findAll();
        }
        return fornecedorRepository.findByRazaoSocialContainingIgnoreCase(razaoSocial.trim());
    }

    public Page<Fornecedor> listar( Integer pagina, Integer tamanho, String razaoSocial) {
        PageRequest pgRequest = PageRequest.of(pagina, tamanho);
        if (razaoSocial == null || razaoSocial.isBlank()) {
            return fornecedorRepository.findAll(pgRequest);
        }
        return fornecedorRepository.findAllByRazaoSocialContainingIgnoreCase(pgRequest, razaoSocial.trim());
    }

    public Fornecedor cadastrar(String razaoSocial, String telefone) {
        validarEntrada(razaoSocial, telefone);
        String razaoSocialNormalizada = normalizarTexto(razaoSocial);

        if (fornecedorRepository.existsByRazaoSocialIgnoreCase(razaoSocialNormalizada)) {
            throw new RecursoExisteException("Ja existe um fornecedor com essa razao social.");
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setRazaoSocial(razaoSocialNormalizada);
        String telefoneNormalizado = normalizarTelefone(telefone);
        fornecedor.setTelefone(telefoneNormalizado);
        fornecedor.setLinkWhatsapp(gerarLinkWhatsapp(telefoneNormalizado));

        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        log.info("Usuário '{}' cadastrou fornecedor ID {} | Razão Social: {} | Tel: {}",
                getUsuarioLogado(), salvo.getId(), salvo.getRazaoSocial(), salvo.getTelefone());
        auditService.registrar(getUsuarioLogado(), "CADASTRO", "FORNECEDOR", salvo.getId(),
                String.format("Cadastrou fornecedor '%s' (tel: %s)", salvo.getRazaoSocial(), salvo.getTelefone()),
                null,
                Map.of("razaoSocial", salvo.getRazaoSocial(), "telefone", salvo.getTelefone()));
        return salvo;
    }

    public Fornecedor atualizar(Integer id, String razaoSocial, String telefone) {
        validarEntrada(razaoSocial, telefone);
        Fornecedor atual = retornarPeloId(id);
        String razaoAnterior = atual.getRazaoSocial();
        String telAnterior = atual.getTelefone();
        String novaRazaoSocial = normalizarTexto(razaoSocial);

        if (!atual.getRazaoSocial().equalsIgnoreCase(novaRazaoSocial)
                && fornecedorRepository.existsByRazaoSocialIgnoreCase(novaRazaoSocial)) {
            throw new RecursoExisteException("Ja existe um fornecedor com essa razao social.");
        }

        atual.setRazaoSocial(novaRazaoSocial);
        String telefoneNormalizado = normalizarTelefone(telefone);
        atual.setTelefone(telefoneNormalizado);
        atual.setLinkWhatsapp(gerarLinkWhatsapp(telefoneNormalizado));
        Fornecedor atualizado = fornecedorRepository.save(atual);
        log.info("Usuário '{}' atualizou fornecedor ID {} | Razão Social: {} → {} | Tel: {} → {}",
                getUsuarioLogado(), id, razaoAnterior, atualizado.getRazaoSocial(), telAnterior, atualizado.getTelefone());
        auditService.registrar(getUsuarioLogado(), "ATUALIZACAO", "FORNECEDOR", id,
                String.format("Atualizou fornecedor '%s'", atualizado.getRazaoSocial()),
                Map.of("razaoSocial", razaoAnterior, "telefone", telAnterior),
                Map.of("razaoSocial", atualizado.getRazaoSocial(), "telefone", atualizado.getTelefone()));
        return atualizado;
    }

    public void deletar(Integer id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Nao foi encontrado um fornecedor com o id %d", id));
        }
        Fornecedor fornecedor = fornecedorRepository.findById(id).orElse(null);
        log.info("Usuário '{}' deletou fornecedor ID {} | Razão Social: {}",
                getUsuarioLogado(), id, fornecedor != null ? fornecedor.getRazaoSocial() : "N/A");
        auditService.registrar(getUsuarioLogado(), "DELECAO", "FORNECEDOR", id,
                String.format("Deletou fornecedor '%s'", fornecedor != null ? fornecedor.getRazaoSocial() : "ID " + id),
                fornecedor != null ? Map.of("razaoSocial", fornecedor.getRazaoSocial(), "telefone", fornecedor.getTelefone()) : null,
                null);
        fornecedorRepository.deleteById(id);
    }

    private void validarEntrada(String razaoSocial, String telefone) {
        if (razaoSocial == null || razaoSocial.isBlank()) {
            throw new EntradaInvalidaException("A razao social do fornecedor nao pode ser vazia.");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new EntradaInvalidaException("O telefone do fornecedor nao pode ser vazio.");
        }
    }

    private String normalizarTexto(String valor) {
        return valor == null ? null : valor.trim();
    }

    private String normalizarTelefone(String telefone) {
        String somenteDigitos = telefone == null ? "" : telefone.replaceAll("\\D", "");
        if (somenteDigitos.isBlank()) {
            throw new EntradaInvalidaException("O telefone do fornecedor nao pode ser vazio.");
        }
        if (somenteDigitos.length() == 10 || somenteDigitos.length() == 11) {
            return "55" + somenteDigitos;
        }
        if (somenteDigitos.startsWith("55") && (somenteDigitos.length() == 12 || somenteDigitos.length() == 13)) {
            return somenteDigitos;
        }
        throw new EntradaInvalidaException("Telefone invalido. Informe DDD + numero.");
    }

    private String gerarLinkWhatsapp(String telefoneNormalizado) {
        return "https://wa.me/" + telefoneNormalizado;
    }

    private String getUsuarioLogado() {
        String apelido = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByApelido(apelido)
                .map(u -> u.getNome() + " (" + u.getApelido() + ")")
                .orElse(apelido);
    }
}
