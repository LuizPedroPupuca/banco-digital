package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.conta.ContaCorrente;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContaCorrenteResponse {


    private BigDecimal saldo;

    private LocalDateTime dataHoraCriacao;
    public ContaCorrenteResponse(ContaCorrente contaCorrente) {
        saldo = contaCorrente.getSaldo();
        dataHoraCriacao = contaCorrente.getDataHoraCriacao();
    }

    public ContaCorrenteResponse() {
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }
}
