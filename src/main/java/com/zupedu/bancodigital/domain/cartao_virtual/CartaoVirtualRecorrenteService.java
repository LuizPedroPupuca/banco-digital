package com.zupedu.bancodigital.domain.cartao_virtual;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;



@Service
public class CartaoVirtualRecorrenteService {

    @Autowired
    CartaoRecorrenteQuerysRepository repository;
    public List<CartaoVirtualRecorrente> buscaTodosCartocesRecorrentes() {
        return repository.listaTodosCartoesRecorrentes();
    }

    public void atualizaStatus(List<CartaoVirtualRecorrente> cartoesVirtuaisRecorrentes) {
        cartoesVirtuaisRecorrentes
                .stream()
                .filter(cartao -> LocalDate.now().isAfter(cartao.getDataValidade()))
                .forEach(cartao -> cartao
                        .setAtivo(false));
        repository.salvaTudo(cartoesVirtuaisRecorrentes);
    }
}
