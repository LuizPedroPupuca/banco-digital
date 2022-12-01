package com.zupedu.bancodigital.adapters.persistence.transacao;

import com.zupedu.bancodigital.domain.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
