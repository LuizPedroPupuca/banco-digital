package com.zupedu.bancodigital.application.produto.output;

import com.zupedu.bancodigital.domain.produto.Produto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class ProdutoResponse {

    private Integer qtde;

    private String serialNumber;

    private String descricao;

    private String nome;

    private BigDecimal valor;

    public ProdutoResponse(Produto produto) {
        this.qtde = produto.getQtde();
        this.serialNumber = produto.getSerialNumber();
        this.descricao = produto.getDescricao();
        this.nome = produto.getNome();
        this.valor = produto.getValor();
    }

    public ProdutoResponse() {
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
}
