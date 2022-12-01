package com.zupedu.bancodigital.domain.transacao;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.produto.Produto;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Transacao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Produto produto;

    private int qtde;

    private String numeroCartao;

    @ManyToOne
    private Conta contaOrigem;

    private String numeroBoleto;

    @ManyToOne
    private Conta contaDestino;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoTransacao tipoTransacao;

    private LocalDateTime dataHoraMovimentacao = LocalDateTime.now();


    public Transacao(Conta contaOrigem, BigDecimal valor) {
        this.contaOrigem = contaOrigem;
        this.valor = valor;
    }

    public Transacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {
        this.contaOrigem = contaOrigem;
        this.contaDestino = contaDestino;
        this.valor = valor;
    }

    public Transacao(Produto produto, String numeroCartao, BigDecimal valor, int qtde, Conta contaOrigem) {
        this.produto = produto;
        this.numeroCartao = numeroCartao;
        this.valor = valor.multiply(BigDecimal.valueOf(qtde));
        this.qtde = qtde;
        this.contaOrigem = contaOrigem;
    }

    @Deprecated
    public Transacao() {
    }

    public Long getId() {
        return id;
    }

    public Conta getContaOrigem() {
        return contaOrigem;
    }

    public Conta getContaDestino() {
        return contaDestino;
    }

    public void setContaDestino(Conta contaDestino) {
        this.contaDestino = contaDestino;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public LocalDateTime getDataHoraMovimentacao() {
        return dataHoraMovimentacao;
    }

    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    public void setNumeroBoleto(String numeroBoleto) {
        this.numeroBoleto = numeroBoleto;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public int getQtde() {
        return qtde;
    }
}
