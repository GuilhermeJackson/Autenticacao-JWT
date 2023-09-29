package com.novidades.gestaodeprojetos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.novidades.gestaodeprojetos.model.Usuario;

@Repository
@EnableJpaRepositories
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
}
