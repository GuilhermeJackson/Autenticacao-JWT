package com.novidades.gestaodeprojetos.service;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.novidades.gestaodeprojetos.model.Usuario;
import com.novidades.gestaodeprojetos.repository.UsuarioRepository;
import com.novidades.gestaodeprojetos.security.JWTService;
import com.novidades.gestaodeprojetos.shared.UsuarioDTO;
import com.novidades.gestaodeprojetos.view.usuario.LoginResponse;

@Service
public class UsuarioService {

    private static final String headerPrefix = "Bearer ";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public List<UsuarioDTO> obterTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTOConvertidos = usuarios.stream()
                .map((item) -> new ModelMapper().map(item, UsuarioDTO.class))
                .collect(Collectors.toList());
        return usuariosDTOConvertidos;
    }

    public Optional<UsuarioDTO> obterPorId(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        UsuarioDTO usuarioDTOConvertido = new ModelMapper().map(usuario.get(), UsuarioDTO.class);
        return Optional.of(usuarioDTOConvertido);
    }

    public Optional<UsuarioDTO> obterPorEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        UsuarioDTO usuarioDTOConvertido = new ModelMapper().map(usuario.get(), UsuarioDTO.class);
        return Optional.of(usuarioDTOConvertido);
    }

    public UsuarioDTO adicionar(UsuarioDTO usuario) {
        if (obterPorEmail(usuario.getEmail()).isPresent()) {
            throw new InputMismatchException(
                    "Já existe um usuário cadastrado com o e-mail: '" + usuario.getEmail() + "'");
        }

        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        Usuario usuarioConvetido = new ModelMapper().map(usuario, Usuario.class);
        usuarioConvetido.setId(null);
        usuarioRepository.save(usuarioConvetido);
        return usuario;
    }

    //
    public LoginResponse logar(String email, String senha) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha, Collections.emptyList()));

        // Spring Security recebe autenticação valida
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = headerPrefix + jwtService.gerarToken(authentication);
        Usuario usuario = usuarioRepository.findByEmail(email).get();

        return new LoginResponse(token, usuario);
    }
}
