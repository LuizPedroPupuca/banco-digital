package com.zupedu.bancodigital.application.transacao.input;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.produto.Produto;
import com.zupedu.bancodigital.domain.transacao.CompraRequest;
import com.zupedu.bancodigital.domain.transacao.Transacao;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CompraCreditoRequest implements CompraRequest {

    @NotBlank
    private String numeroCartao;

    @NotBlank
    private String cv;

    @NotNull
    private LocalDate validade;

    private Long idProduto;

    private int qtde;

    public CompraCreditoRequest(String numeroCartao, String cv, LocalDate validade, Long idProduto, int qtde) {
        this.numeroCartao = numeroCartao;
        this.cv = cv;
        this.validade = validade;
        this.idProduto = idProduto;
        this.qtde = qtde;
    }

    public CompraCreditoRequest() {
    }

    @Override
    public Transacao toModel(Produto produto, Conta contaOrigem) {
        return new Transacao(produto, numeroCartao, produto.getValor(), qtde, contaOrigem );
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getCv() {
        return cv;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public int getQtde() {
        return qtde;
    }
}
