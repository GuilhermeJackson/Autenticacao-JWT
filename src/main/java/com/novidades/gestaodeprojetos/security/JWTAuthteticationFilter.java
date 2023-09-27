package com.novidades.gestaodeprojetos.security;

import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.novidades.gestaodeprojetos.model.Usuario;

@Component
public class JWTAuthteticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // Valida a autenticação do usuário sempre antes de bater no end-point
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenDaRequisição = obterToken(request);
        Optional<Long> idUsuarioDoToken = jwtService.obterIdDoUsuario(tokenDaRequisição);

        if (!idUsuarioDoToken.isPresent()) {
            throw new InputMismatchException("Token inválido!");
        }
        Usuario usuario = customUserDetailService.obterUsuarioPorId(idUsuarioDoToken.get());
        UsernamePasswordAuthenticationToken autenticacao = new UsernamePasswordAuthenticationToken(usuario, null,
                Collections.emptyList());
        autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(autenticacao);
    }

    private String obterToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (StringUtils.hasText(token)) {
            return null;
        }
        return token.substring(7, 0);
    }
}
