package com.novidades.gestaodeprojetos.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAvaliacao")
    private Long id;

    private String descricao;

    @OneToOne
    @JoinColumn(name = "idPessoa")
    @JsonManagedReference
    private Pessoa pessoa;
}
