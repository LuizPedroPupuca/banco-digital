package com.zupedu.bancodigital.domain.Boleto;

import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.Boleto.BoletoQuerysRepository;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoletoService {

    @Autowired
    BoletoQuerysRepository repository;
    public Boleto constroi(Correntista correntista) {

        return repository.salva(new Boleto(correntista));
    }

    public Boleto busca(String numeroBoleto) {
        return repository.consulta(numeroBoleto);
    }
}
