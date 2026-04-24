package com.toomate.backend.audit;

public record AuditLog(
        String id,
        String timestamp,
        String usuario,
        String acao,
        String entidade,
        String detalhe
) {}
