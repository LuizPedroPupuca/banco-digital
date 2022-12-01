package com.zupedu.bancodigital.adapters.persistence.conta;

import com.zupedu.bancodigital.domain.conta.Conta;

import com.zupedu.bancodigital.domain.conta.ContaCorrente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaCorrenteRepository extends JpaRepository<ContaCorrente, Long> {
}
