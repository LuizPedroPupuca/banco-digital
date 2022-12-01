package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.conta.ContaPoupanca;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaPoupancaResponse {

    private BigDecimal saldo;

    private LocalDateTime dataHoraCriacao;


    public ContaPoupancaResponse(ContaPoupanca contaPoupanca) {
        saldo = contaPoupanca.getSaldo();
        dataHoraCriacao = contaPoupanca.getDataHoraCriacao();
    }

    public ContaPoupancaResponse() {
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }
}
