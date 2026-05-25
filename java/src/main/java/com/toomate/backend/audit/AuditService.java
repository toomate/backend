package com.toomate.backend.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AuditService {

    private final S3Client s3Client;
    private final ObjectMapper mapper = new ObjectMapper();

    public AuditService(@Value("${audit.aws-region:${AWS_REGION:us-east-1}}") String awsRegion) {
        this.s3Client = S3Client.builder()
                .region(Region.of(awsRegion))
                .build();
    }

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

    public List<AuditLog> listar(LocalDate data) {
        String prefixoDia = String.format("%s/%s/", prefix, data);

        List<S3Object> objetos = s3Client.listObjectsV2(
                ListObjectsV2Request.builder()
                        .bucket(bucket)
                        .prefix(prefixoDia)
                        .build()
        ).contents();

        List<AuditLog> logs = new ArrayList<>();
        for (S3Object objeto : objetos) {
            try {
                byte[] conteudo = s3Client.getObjectAsBytes(
                        b -> b.bucket(bucket).key(objeto.key())
                ).asByteArray();
                logs.add(mapper.readValue(conteudo, AuditLog.class));
            } catch (Exception e) {
                log.error("Falha ao ler log de auditoria {}: {}", objeto.key(), e.getMessage());
            }
        }
        return logs;
    }

    public Page<AuditLog> listarPaginado(LocalDate data, int pagina, int tamanho) {
        List<AuditLog> todos = new ArrayList<>(listar(data));
        todos.sort((a, b) -> {
            String ta = a.timestamp() == null ? "" : a.timestamp();
            String tb = b.timestamp() == null ? "" : b.timestamp();
            return tb.compareTo(ta);
        });

        int total = todos.size();
        Pageable pageable = PageRequest.of(pagina, tamanho);
        int inicio = (int) pageable.getOffset();

        if (inicio >= total) {
            return new PageImpl<>(List.of(), pageable, total);
        }

        int fim = Math.min(inicio + tamanho, total);
        return new PageImpl<>(todos.subList(inicio, fim), pageable, total);
    }
}
