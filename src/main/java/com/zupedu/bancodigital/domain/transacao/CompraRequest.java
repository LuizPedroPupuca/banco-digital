package com.zupedu.bancodigital.domain.transacao;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.produto.Produto;

public interface CompraRequest {

    Transacao toModel(Produto produto, Conta contaOrigem);
}
