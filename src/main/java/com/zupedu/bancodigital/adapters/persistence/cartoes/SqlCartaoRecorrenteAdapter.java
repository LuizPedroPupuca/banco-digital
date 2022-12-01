package com.zupedu.bancodigital.adapters.persistence.cartoes;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoRecorrenteQuerysRepository;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Component
public class SqlCartaoRecorrenteAdapter implements CartaoRecorrenteQuerysRepository {

    @Autowired
    CartaoVirtualRecorrenteRepository repository;

    @Override
    public List<CartaoVirtualRecorrente> listaTodosCartoesRecorrentes() {
         return repository.findAll();
    }

    @Override
    public void salvaTudo(List<CartaoVirtualRecorrente> cartoesVirtuaisRecorrentes) {
        repository.saveAll(cartoesVirtuaisRecorrentes);
    }

    @Override
    public CartaoVirtualRecorrente retornaCvr(String numeroCartao, String cv, LocalDate validade) {
         return repository.findByNumeroAndCvAndDataValidade(numeroCartao, cv, validade).orElseThrow(()
                 -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão inexistente"));
    }
}
