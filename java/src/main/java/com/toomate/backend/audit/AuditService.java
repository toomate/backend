package com.toomate.backend.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class AuditService {

    private final S3Client s3Client = S3Client.builder().build();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${audit.bucket}")
    private String bucket;

    @Value("${audit.prefix:logs/auditoria}")
    private String prefix;

    @Async
    public void registrar(String usuario, String acao, String entidade, String detalhe) {
        try {
            String id = UUID.randomUUID().toString();
            LocalDateTime agora = LocalDateTime.now();

            AuditLog evento = new AuditLog(id, agora.toString(), usuario, acao, entidade, detalhe);

            String json = mapper.writeValueAsString(evento);

            String chave = String.format("%s/%s/%s-%s.json",
                    prefix,
                    LocalDate.now(),
                    agora.toString().replace(":", "-").replace(".", "-"),
                    id.substring(0, 8));

            s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucket)
                            .key(chave)
                            .contentType("application/json")
                            .build(),
                    RequestBody.fromString(json));

        } catch (Exception e) {
            log.error("Falha ao salvar log de auditoria no S3: {}", e.getMessage());
        }
    }
}
