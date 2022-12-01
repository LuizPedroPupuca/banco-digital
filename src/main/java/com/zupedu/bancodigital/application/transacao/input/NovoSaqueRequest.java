package com.zupedu.bancodigital.application.transacao.input;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.SaqueRequest;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NovoSaqueRequest implements SaqueRequest {

    @NotNull
    private BigDecimal valor;

    public NovoSaqueRequest(BigDecimal valor) {
        this.valor = valor;
    }

    public NovoSaqueRequest() {
    }

    @Override
    public Transacao toModel(Conta conta) {
        conta.getContaCorrente().debita(valor);
        return new Transacao(conta, valor);
    }

    public BigDecimal getValor() {
        return valor;
    }
}
