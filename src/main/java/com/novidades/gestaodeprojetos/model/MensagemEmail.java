package com.novidades.gestaodeprojetos.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensagemEmail {
    private String assunto;
    private String mensagem;
    private String remetente;
    private List<String> destinatarios;
}
