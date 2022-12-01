package com.zupedu.bancodigital.adapters.persistence.conta;
import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import com.zupedu.bancodigital.domain.conta.ContaQuerysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class SqlContaAdapter implements ContaQuerysRepository {

    @Autowired
    ContaPoupancaRepository contaPoupancaRepository;

    @Autowired
    ContaInvestimentoRepository contaInvestimentoRepository;

    @Override
    public List<ContaPoupanca> consultaTodasContasPoupanca() {
        return contaPoupancaRepository.findAll();
    }

    @Override
    public void salvaTudoContaPoupanca(List<ContaPoupanca> contasPoupanca) {
        contaPoupancaRepository.saveAll(contasPoupanca);
    }

    @Override
    public List<ContaInvestimento> consultaTodasContasInvestimento() {
        return contaInvestimentoRepository.findAll();
    }

    @Override
    public void salvaTudoContaInvestimento(List<ContaInvestimento> contasInvestimento) {
        contaInvestimentoRepository.saveAll(contasInvestimento);
    }
}
