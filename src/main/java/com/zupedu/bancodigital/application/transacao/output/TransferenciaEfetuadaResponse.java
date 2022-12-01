package com.zupedu.bancodigital.application.transacao.output;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransferenciaEfetuadaResponse {

    private String valor;
    private String contaDestino;
    private String tipoTransacao;
    private LocalDateTime dataHoraMovimentacao;

    public TransferenciaEfetuadaResponse(Transacao transacao){
        valor = String.valueOf(transacao.getValor());
        contaDestino = retornaContaDestinoExistente(transacao);
        tipoTransacao = String.valueOf(transacao.getTipoTransacao());
        dataHoraMovimentacao = transacao.getDataHoraMovimentacao();
    }

    public TransferenciaEfetuadaResponse() {
    }

    private String retornaContaDestinoExistente(Transacao transacao){
        Optional<Conta> possivelContaDestino = Optional.ofNullable(transacao.getContaDestino());
        if(possivelContaDestino.isPresent()){
            return contaDestino = transacao.getContaDestino().getNumero();
        }
        return contaDestino = "";

    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getContaDestino() {
        return contaDestino;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public LocalDateTime getDataHoraMovimentacao() {
        return dataHoraMovimentacao;
    }
}
