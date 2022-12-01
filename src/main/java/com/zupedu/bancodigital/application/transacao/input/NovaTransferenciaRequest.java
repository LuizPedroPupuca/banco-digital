package com.zupedu.bancodigital.application.transacao.input;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.TransferenciaRequest;
import com.zupedu.bancodigital.domain.transacao.TipoTransacao;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class NovaTransferenciaRequest implements TransferenciaRequest {

    @NotNull
    private BigDecimal valor;

    public NovaTransferenciaRequest(BigDecimal valor) {
        this.valor = valor;
    }

    public NovaTransferenciaRequest() {
    }

    public BigDecimal getValor() {
        return valor;
    }


    @Override
    public Transacao toModel(Conta contaOrigem, Conta contaDestino) {
        contaOrigem.getContaCorrente().debita(valor);
        contaDestino.getContaCorrente().credita(valor);
        Transacao transacao = new Transacao(contaOrigem, contaDestino, valor);
        transacao.setTipoTransacao(TipoTransacao.TRANSFERENCIA);
        return transacao;
    }
}
