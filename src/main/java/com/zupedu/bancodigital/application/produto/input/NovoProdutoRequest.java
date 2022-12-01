package com.zupedu.bancodigital.application.produto.input;

import com.zupedu.bancodigital.domain.produto.Produto;
import com.zupedu.bancodigital.domain.produto.ProdutoRequest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NovoProdutoRequest implements ProdutoRequest {
    @NotNull
    private Integer qtde;
    @NotBlank
    private String serialNumber;
    @NotBlank
    private String descricao;
    @NotBlank
    private String nome;
    @NotNull
    private BigDecimal valor;

    public NovoProdutoRequest(Integer qtde, String serialNumber, String descricao, String nome, BigDecimal valor) {
        this.qtde = qtde;
        this.serialNumber = serialNumber;
        this.descricao = descricao;
        this.nome = nome;
        this.valor = valor;
    }

    public NovoProdutoRequest() {
    }

    @Override
    public Produto toModel() {
        return new Produto(qtde, serialNumber, descricao, nome, valor);
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
