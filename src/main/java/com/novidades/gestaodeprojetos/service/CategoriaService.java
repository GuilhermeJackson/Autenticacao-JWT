package com.novidades.gestaodeprojetos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.novidades.gestaodeprojetos.model.Categoria;
import com.novidades.gestaodeprojetos.repository.CategoriaRepository;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> obterTodos() {
        return categoriaRepository.findAll();
    }

    @GetMapping
    public Optional<Categoria> obterPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    @PostMapping
    public Categoria adicionar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @PutMapping
    public Categoria atualizar(Long id, Categoria categoria) {
        categoria.setId(id);
        return categoriaRepository.save(categoria);
    }

    @DeleteMapping
    public void deletar(Long id) {
        categoriaRepository.deleteById(id);
    }
}
