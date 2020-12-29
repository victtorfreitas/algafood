package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class Restaurante {

    @Id
    private Long id;
    private String nome;
    @Column(name = "taxa_frete")
    private BigDecimal taxaFrete;
}
