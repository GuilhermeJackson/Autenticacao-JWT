package com.novidades.gestaodeprojetos.view.usuario;

import com.novidades.gestaodeprojetos.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private Usuario usuario;
}
