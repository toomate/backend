package com.toomate.backend.integration;

import com.toomate.backend.exceptions.*;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;


public class S3Uploader {
    public static final S3Client s3Client = S3Client.builder().build();

    public static byte[] getImage(String bucketName, String objectKey) {
        System.out.println("Buscando Imagem");
        GetObjectRequest object = GetObjectRequest.builder()
                .key(objectKey)
                .bucket(bucketName)
                .build();
        ResponseBytes<GetObjectResponse> bytes = null;

        try {
            bytes = s3Client.getObjectAsBytes(object);
        } catch (SdkClientException e) {
            throw new SdkNaoConfiguradoException("O Cliente Amazon SDK não está configurado! " + e);
        } catch (S3Exception e) {
            switch (e.statusCode()) {
                case 403:
                    throw new PermissaoNegadaException("Acesso negado a imagem S3 " + e);
                case 404:
                    throw new ObjetoNaoEncontradoException("Imagem não encontrada! " + e);
                case 500:
                    throw new ErroInternoException("Erro interno no serviço S3 " + e);
                case 503:
                    throw new ServicoIndisponivelException("Serviço indisponível, tente novamente mais tarde " + e);
                default:
                    throw new RuntimeException("Erro S3 inesperado: " + e.awsErrorDetails());
            }
        }
        System.out.println("Imagem encontrada com sucesso!");

        return bytes.asByteArray();
    }

    public static void putImage(String bucketName, String fileName, byte[] archive) {
        if (alreadyExists(bucketName, fileName)) {
            throw new RecursoExisteException(String.format("Já existe uma imagem cadastrada com o nome '%s!!'", fileName));
        }
        System.out.println("Enviando imagem para o bucket " + bucketName);
        PutObjectRequest object = PutObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .build();

        exceptionHandling(() -> {
        s3Client.putObject(object, RequestBody.fromBytes(archive));
        System.out.println("Imagem enviada com sucesso!");
        });
    }


    public static void updateImage(String bucketName, String fileName, byte[] archive) {
        System.out.println("Enviando imagem para bucket " + bucketName);
        PutObjectRequest object = PutObjectRequest.builder()
                .key(fileName)
                .bucket(bucketName)
                .build();
        exceptionHandling(() -> {
            s3Client.putObject(object, RequestBody.fromBytes(archive));
            System.out.println("Imagem enviada com sucesso!");
        });

    }

    public static void deleteImage(String bucketName, String objectKey) {
        System.out.println("Buscando Imagem");
        DeleteObjectRequest object = DeleteObjectRequest.builder()
                .key(objectKey)
                .bucket(bucketName)
                .build();
        System.out.println("Imagem encontrada");

        exceptionHandling(() -> {
            s3Client.deleteObject(object);
            System.out.println("Imagem deletada com sucesso!");
        });
    }

    private static Boolean alreadyExists(String bucketName, String objectKey) {
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(objectKey)
                    .build();

            s3Client.headObject(headObjectRequest);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        }
    }

    private static void exceptionHandling(Runnable operation) {
        try {
            operation.run();
        } catch (SdkClientException e) {
            throw new SdkNaoConfiguradoException("O Cliente Amazon SDK não está configurado! " + e);
        } catch (S3Exception e) {
            switch (e.statusCode()) {
                case 403:
                    throw new PermissaoNegadaException("Acesso negado à imagem S3 " + e);
                case 404:
                    throw new ObjetoNaoEncontradoException("Imagem não encontrada! " + e);
                case 500:
                    throw new ErroInternoException("Erro interno no serviço S3 " + e);
                case 503:
                    throw new ServicoIndisponivelException("Serviço indisponível, tente novamente mais tarde " + e);
                default:
                    throw new RuntimeException("Erro S3 inesperado: " + e.awsErrorDetails());
            }
        }
    }

}
