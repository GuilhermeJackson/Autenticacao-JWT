package com.novidades.gestaodeprojetos.security;

import java.util.Optional;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.novidades.gestaodeprojetos.model.Usuario;
import com.novidades.gestaodeprojetos.service.UsuarioService;
import com.novidades.gestaodeprojetos.shared.UsuarioDTO;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UsuarioDTO> usuarioDTO = usuarioService.obterPorEmail(email);

        Usuario usuarioConvertido = new ModelMapper().map(usuarioDTO, Usuario.class);

        Usuario usuario = getUser(() -> Optional.of(usuarioConvertido));
        return usuario;
    }

     @Transactional
    public Usuario obterUsuarioPorId(Long id) {
        UsuarioDTO usuarioDTO = usuarioService.obterPorId(id).get();
        Usuario usuario = new ModelMapper().map(usuarioDTO, Usuario.class);
        return usuario;
    }

    private Usuario getUser(Supplier<Optional<Usuario>> supplier) {
        return supplier.get().orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado!"));
    }

}
