package com.projeto.sistema.modelos;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "entradaProduto")

public class EntradaProduto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String obs;
    private Double valorTotal = 0.00;
    private Double quantidadeTotal = 0.00;
    private Date dataEntrada = new Date();

    @ManyToOne
    private Produto produto;

    @ManyToOne
    private ItemEntrada itemEntrada;

    @ManyToOne
    private Fornecedor fornecedor;

    @ManyToOne
    private Funcionario funcionario;

}
