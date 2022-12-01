package com.zupedu.bancodigital.domain.cartao_virtual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CartaoVirtualTemporarioService {

    @Autowired
    CartaoTemporarioQuerysRepository repository;

    public List<CartaoVirtualTemporario> buscaTodosCartoesTemporarios() {
        return repository.listaTodosCartoesTemporarios();
    }

    public void atualizaStatus(List<CartaoVirtualTemporario> cartoesVirtuaisTemporarios) {
        cartoesVirtuaisTemporarios
                .stream()
                .filter(cartao ->
                        LocalDate.now().isAfter(cartao.getDataValidade()))
                .forEach(cartao -> cartao.setAtivo(false));
        repository.salvaTudo(cartoesVirtuaisTemporarios);
    }
}
