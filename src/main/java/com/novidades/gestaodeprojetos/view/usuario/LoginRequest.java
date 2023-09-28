package com.novidades.gestaodeprojetos.view.usuario;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}
