package com.novidades.gestaodeprojetos.service;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.novidades.gestaodeprojetos.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    private static final String chavePrivadaJWT = "secret_key";

    public String gerarToken(Authentication authentication) {
        int tempoExpiracao = 900000;

        Date dataExpiracao = new Date(new Date().getTime() + tempoExpiracao);

        // Pega usuário atual da autenticação
        Usuario usuario = (Usuario) authentication.getPrincipal();

        String TokenOfJWT = Jwts.builder()
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS512, chavePrivadaJWT)
                .compact();

        return TokenOfJWT;
    }
}
