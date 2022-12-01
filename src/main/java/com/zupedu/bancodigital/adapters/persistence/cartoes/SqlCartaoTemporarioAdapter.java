package com.zupedu.bancodigital.adapters.persistence.cartoes;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoTemporarioQuerysRepository;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SqlCartaoTemporarioAdapter implements CartaoTemporarioQuerysRepository {

    @Autowired
    CartaoVirtualTemporarioRepository repository;
    @Override
    public List<CartaoVirtualTemporario> listaTodosCartoesTemporarios() {
        return repository.findAll();
    }

    @Override
    public void salvaTudo(List<CartaoVirtualTemporario> cartoesVirtuaisTemporarios) {
        repository.saveAll(cartoesVirtuaisTemporarios);
    }
}
