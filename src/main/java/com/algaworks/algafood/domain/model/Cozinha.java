package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Cozinha {

    @Id
    private Long id;
    private String nome;
}
