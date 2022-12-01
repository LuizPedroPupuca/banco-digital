package com.zupedu.bancodigital.application.transacao.output;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import java.time.LocalDateTime;
import java.util.Optional;

public class TransferenciaRecebidaResponse {

    private String valor;
    private String contaOrigem;
    private String tipoTransacao;
    private LocalDateTime dataHoraMovimentacao;

    public TransferenciaRecebidaResponse(Transacao transacao){
        valor = String.valueOf(transacao.getValor());
        contaOrigem = transacao.getContaOrigem().getNumero();
        tipoTransacao = String.valueOf(transacao.getTipoTransacao());
        dataHoraMovimentacao = transacao.getDataHoraMovimentacao();
    }

    public TransferenciaRecebidaResponse() {
    }

//    private String retornaContaDestinoExistente(Transacao transacao){
//        Optional<Conta> possivelContaDestino = Optional.ofNullable(transacao.getContaDestino());
//        if(possivelContaDestino.isPresent()){
//            return contaDestino = transacao.getContaDestino().getNumero();
//        }
//        return contaDestino = "";
//
//    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getContaOrigem() {
        return contaOrigem;
    }

    public String getTipoTransacao() {
        return tipoTransacao;
    }

    public LocalDateTime getDataHoraMovimentacao() {
        return dataHoraMovimentacao;
    }
}
