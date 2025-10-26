package com.toomate.backend.integration;

import com.toomate.backend.model.Insumo;
import org.springframework.stereotype.Component;

@Component
public class EnviarNotificacao {

    public void enviarNotif(Insumo insumo, Double atual){
        try{
        System.out.printf("QUANTIDADE DE ESTOQUE MENOR DO QUE O MÍNIMO\nINSUMO: %s | Estoque Min: %s | Estoque atual: %s\n", insumo.getNome(), insumo.getQtdMinima().toString(), atual.toString());
        } catch (RuntimeException e){
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
