package com.zupedu.bancodigital.domain.transacao;

import com.zupedu.bancodigital.domain.conta.Conta;

public interface TransferenciaRequest {

    Transacao toModel(Conta contaOrigem, Conta contaDestino);
}
