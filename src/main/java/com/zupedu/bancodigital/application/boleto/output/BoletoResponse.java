package com.zupedu.bancodigital.application.boleto.output;

import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.Boleto.Status;

import java.math.BigDecimal;

public class BoletoResponse {

    private String numero;

    private BigDecimal valor;

    private Status status;

    public BoletoResponse() {
    }

    public BoletoResponse(Boleto boleto) {
        numero = boleto.getNumero();
        valor = boleto.getValor();
        status = boleto.getStatus();
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Status getStatus() {
        return status;
    }
}
