package com.toomate.backend.audit;

import java.util.Map;

public record AuditLog(
        String id,
        String timestamp,
        String usuario,
        String acao,
        String entidade,
        Integer entidadeId,
        String detalhe,
        Map<String, Object> dadosAnteriores,
        Map<String, Object> dadosNovos
) {
    public AuditLog(String id, String timestamp, String usuario, String acao, String entidade, String detalhe) {
        this(id, timestamp, usuario, acao, entidade, null, detalhe, null, null);
    }

    public AuditLog(String id, String timestamp, String usuario, String acao, String entidade, Integer entidadeId, String detalhe) {
        this(id, timestamp, usuario, acao, entidade, entidadeId, detalhe, null, null);
    }
}
