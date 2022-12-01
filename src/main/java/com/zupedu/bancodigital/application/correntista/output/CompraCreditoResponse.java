package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.transacao.Transacao;

import java.time.LocalDateTime;

public class CompraCreditoResponse {

    private String valor;
    private String tipoTransacao;
    private LocalDateTime dataHoraMovimentacao;
    private String numeroCartao;
    private int qtde;

    private String nomeProdudto;

    private String descricaoProduto;

    public CompraCreditoResponse(Transacao transacao){
        valor = String.valueOf(transacao.getValor());
        tipoTransacao = String.valueOf(transacao.getTipoTransacao());
        dataHoraMovimentacao = transacao.getDataHoraMovimentacao();
        numeroCartao = transacao.getNumeroCartao();
        qtde = transacao.getQtde();
    }

    public CompraCreditoResponse() {
    }


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public LocalDateTime getDataHoraMovimentacao() {
        return dataHoraMovimentacao;
    }
}

