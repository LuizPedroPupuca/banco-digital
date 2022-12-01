package com.zupedu.bancodigital.adapters.persistence.boleto;

import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.Boleto.BoletoQuerysRepository;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class SqlBoletoAdapter implements BoletoQuerysRepository {

    @Autowired
    private BoletoRepository repository;

    @Override
    public Boleto salva(Boleto boleto) {
        if(repository.findByNumero(boleto.getNumero()).isPresent()){
            throw  new ResponseStatusException((HttpStatus.UNPROCESSABLE_ENTITY), "Número de boleto já existente");
        }
        return repository.save(boleto);
    }

    @Override
    public Boleto consulta(String numeroBoleto) {
        return repository.findByNumero(numeroBoleto).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Boleto não encontrado"));
    }
}
