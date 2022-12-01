package com.zupedu.bancodigital.domain.conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaService {

    @Autowired
    ContaQuerysRepository repository;

    public List<ContaPoupanca> buscaTodasContasPoupanca() {
        return repository.consultaTodasContasPoupanca();
    }

    public void aplicaRendimentoPoupanca(List<ContaPoupanca> contasPoupanca) {
        contasPoupanca.forEach(contaPoupanca -> contaPoupanca.aplicaRendimento());
        repository.salvaTudoContaPoupanca(contasPoupanca);
    }

    public List<ContaInvestimento> buscaTodasContasInvestimento() {
        return repository.consultaTodasContasInvestimento();
    }

    public void aplicaRendimentoInvestimento(List<ContaInvestimento> contasInvestimento) {
        contasInvestimento.forEach(contaInvestimento -> contaInvestimento.aplicaRendimento());
        repository.salvaTudoContaInvestimento(contasInvestimento);
    }
}
