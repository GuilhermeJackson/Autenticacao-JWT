package com.novidades.gestaodeprojetos.view.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.novidades.gestaodeprojetos.service.UsuarioService;
import com.novidades.gestaodeprojetos.shared.UsuarioDTO;
import com.novidades.gestaodeprojetos.view.usuario.LoginRequest;
import com.novidades.gestaodeprojetos.view.usuario.LoginResponse;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioDTO> obterTodos() {
        return usuarioService.obterTodos();
    }

    @GetMapping("/{id}")
    public Optional<UsuarioDTO> obterPorId(@PathVariable("id") Long id) {
        return usuarioService.obterPorId(id);
    }

    @PostMapping
    public UsuarioDTO adicionar(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.adicionar(usuarioDTO);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return usuarioService.logar(request.getEmail(), request.getSenha());
    }
}
