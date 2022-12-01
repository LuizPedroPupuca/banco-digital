package com.zupedu.bancodigital.domain.conta;

import com.zupedu.bancodigital.domain.correntista.Correntista;

import java.util.List;

public interface ContaQuerysRepository {
    List<ContaPoupanca> consultaTodasContasPoupanca();

    void salvaTudoContaPoupanca(List<ContaPoupanca> contasPoupanca);

    List<ContaInvestimento> consultaTodasContasInvestimento();

    void salvaTudoContaInvestimento(List<ContaInvestimento> contasInvestimento);
}
