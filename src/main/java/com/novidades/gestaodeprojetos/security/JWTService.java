package com.novidades.gestaodeprojetos.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.novidades.gestaodeprojetos.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    private static final String chavePrivadaJWT = "secret_key";

    public String gerarToken(Authentication authentication) {
        int tempoExpiracao = 900000;
        Date dataExpiracao = new Date(new Date().getTime() + tempoExpiracao);

        // Pega usuário atual da autenticação
        Usuario usuarioAtual = (Usuario) authentication.getPrincipal();

        String TokenOfJWT = Jwts.builder()
                .setSubject(usuarioAtual.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS512, chavePrivadaJWT)
                .compact();

        return TokenOfJWT;
    }

    public Optional<Long> obterIdDoUsuario(String token) {
        try {
            // Claims - objeto de autorização
            Claims claims = parse(token).getBody();
            Optional<Long> idDoUsuario = Optional.ofNullable(Long.parseLong(claims.getSubject()));
            return idDoUsuario;
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Com a chave pricada, busca no Token as permissões do usuário
    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(chavePrivadaJWT).parseClaimsJws(token);
    }
}
