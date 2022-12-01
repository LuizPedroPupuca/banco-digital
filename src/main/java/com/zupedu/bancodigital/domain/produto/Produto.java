package com.zupedu.bancodigital.domain.produto;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer qtde;
    @Column(unique = true)
    private String serialNumber;

    private String descricao;

    private String nome;

    private BigDecimal valor;

    public Produto(Integer qtde, String serialNumber, String descricao, String nome, BigDecimal valor) {
        this.qtde = qtde;
        this.serialNumber = serialNumber;
        this.descricao = descricao;
        this.nome = nome;
        this.valor = valor;
    }

    @Deprecated
    public Produto() {
    }

    public Long getId() {
        return id;
    }

    public Integer getQtde() {
        return qtde;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void descrementa(int qtde) {
        if(qtde > this.qtde || this.qtde == 0){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Quantidade insuficiente");
        }
        this.qtde = this.qtde - qtde;
    }
}
