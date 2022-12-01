package com.zupedu.bancodigital.domain.transacao;

import com.zupedu.bancodigital.domain.conta.Conta;

public interface SaqueRequest {
    Transacao toModel(Conta conta);
}
