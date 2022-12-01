package com.zupedu.bancodigital.application.transacao.output;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import java.time.LocalDateTime;
import java.util.Optional;

public class SaqueResponse {

    private String valor;
    private String tipoTransacao;
    private LocalDateTime dataHoraMovimentacao;

    public SaqueResponse(Transacao transacao){
        valor = String.valueOf(transacao.getValor());
        tipoTransacao = String.valueOf(transacao.getTipoTransacao());
        dataHoraMovimentacao = transacao.getDataHoraMovimentacao();
    }

    public SaqueResponse() {
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
