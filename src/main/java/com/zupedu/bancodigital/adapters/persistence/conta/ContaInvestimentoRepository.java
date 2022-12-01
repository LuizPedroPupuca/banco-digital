package com.zupedu.bancodigital.adapters.persistence.conta;

import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaInvestimentoRepository extends JpaRepository<ContaInvestimento, Long> {
}
