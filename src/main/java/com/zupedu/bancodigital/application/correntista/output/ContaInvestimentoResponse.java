package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.conta.ContaInvestimento;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaInvestimentoResponse {

    private BigDecimal saldo;

    private LocalDateTime dataHoraCriacao;


    public ContaInvestimentoResponse(ContaInvestimento contaInvestimento) {
        saldo = contaInvestimento.getSaldo();
        dataHoraCriacao = contaInvestimento.getDataHoraCriacao();
    }

    public ContaInvestimentoResponse() {
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }
}
