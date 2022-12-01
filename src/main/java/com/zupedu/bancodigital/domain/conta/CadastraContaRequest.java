package com.zupedu.bancodigital.domain.conta;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.correntista.Correntista;

public interface CadastraContaRequest {
    Conta paraConta(Correntista correntista);
}
