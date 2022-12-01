package com.zupedu.bancodigital.application.boleto.output;

import com.zupedu.bancodigital.domain.Boleto.Status;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import java.time.LocalDateTime;

public class PagamentoBoletoResponse {
    private String valor;
    private String tipoTransacao;
    private String numeroBoleto;
    private LocalDateTime dataHoraMovimentacao;

    public PagamentoBoletoResponse(Transacao transacao) {
        this.valor = String.valueOf(transacao.getValor());
        this.tipoTransacao = String.valueOf(transacao.getTipoTransacao());
        this.numeroBoleto = transacao.getNumeroBoleto();
        this.dataHoraMovimentacao = transacao.getDataHoraMovimentacao();
    }

    public PagamentoBoletoResponse() {
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

    public String getNumeroBoleto() {
        return numeroBoleto;
    }

    public LocalDateTime getDataHoraMovimentacao() {
        return dataHoraMovimentacao;
    }
}
