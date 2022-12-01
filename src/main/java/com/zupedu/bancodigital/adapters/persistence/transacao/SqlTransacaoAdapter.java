package com.zupedu.bancodigital.adapters.persistence.transacao;

import com.zupedu.bancodigital.domain.transacao.Transacao;
import com.zupedu.bancodigital.domain.transacao.TransacaoQuerysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlTransacaoAdapter implements TransacaoQuerysRepository {

    @Autowired
    private TransacaoRepository repository;

    @Override
    public Transacao salva(Transacao transacao) {
        return repository.save(transacao);
    }
}
