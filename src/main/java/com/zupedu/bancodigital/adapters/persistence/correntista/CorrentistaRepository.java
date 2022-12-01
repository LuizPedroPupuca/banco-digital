package com.zupedu.bancodigital.adapters.persistence.correntista;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CorrentistaRepository extends JpaRepository<Correntista, Long> {
    Boolean existsByDocumento(String documento);

    boolean existsByConta_ContaPoupanca_Conta_Id(Long idConta);

    boolean existsByConta_ContaInvestimento_Conta_Id(Long idConta);

    Optional<Correntista> findByDocumento(String documento);

    Optional<Correntista> findByConta_Numero(String username);
}
