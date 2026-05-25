package com.toomate.backend.controller;

import com.toomate.backend.audit.AuditLog;
import com.toomate.backend.audit.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/audit-logs")
@SecurityRequirement(name = "Bearer")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @Operation(summary = "Listar logs de auditoria por data",
               description = "Retorna os eventos de auditoria de um dia. Padrão: hoje.")
    @GetMapping
    public ResponseEntity<List<AuditLog>> listar(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        List<AuditLog> logs = auditService.listar(dataConsulta);

        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Listar logs de auditoria paginado",
               description = "Retorna uma página de eventos de auditoria de um dia, ordenados do mais recente para o mais antigo.")
    @GetMapping("/paginado")
    public ResponseEntity<Page<AuditLog>> listarPaginado(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "20") int tamanho) {

        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        Page<AuditLog> logs = auditService.listarPaginado(dataConsulta, pagina, tamanho);

        if (logs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(logs);
    }
}
