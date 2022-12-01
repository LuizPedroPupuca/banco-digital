package com.zupedu.bancodigital.adapters.persistence.correntista;


import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import com.zupedu.bancodigital.domain.correntista.CorrentistaQuerysRepository;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SqlCorrentistaAdapter implements CorrentistaQuerysRepository {

    @Autowired
    CorrentistaRepository repository;

    @Override
    public List<Correntista> salvaTudo(List<Correntista> correntistas) {
        repository.saveAll(correntistas);
        correntistas.forEach(correntista ->  correntista.getConta().setNumero("000"+correntista.getConta().getId()+"-0"));
        return repository.saveAll(correntistas);
    }

    @Override
    public Correntista salva(Correntista correntista) {
        return repository.save(correntista);
    }

    @Override
    public Correntista consulta(String documento) {
        return repository.findByDocumento(documento).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta Poupança já existente"));
    }



    @Override
    public void deleta(Correntista correntista) {
        repository.delete(correntista);
    }

    @Override
    public List<Correntista> consultaTodos() {
        return repository.findAll();
    }

    @Override
    public Boolean verifica(String documento) {
        var valor  = repository.existsByDocumento(documento);
        if (valor){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Correntista já cadastrado");

        }
        return valor;
    }

    @Override
    public void verificaContaPoupanca(Long idConta) {
        if(repository.existsByConta_ContaPoupanca_Conta_Id(idConta)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Conta Poupança já existente");
        }
    }

    @Override
    public void verificaContaInvestimento(Long idConta) {
        if(repository.existsByConta_ContaInvestimento_Conta_Id(idConta)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Conta Investimento já existente");
        }
    }

    @Override
    public List<ContaPoupanca> consultaTodasContasPoupanca() {
        return repository.findAll().stream().map(correntista -> correntista.getConta().getContaPoupanca()).collect(Collectors.toList());
    }

    @Override
    public List<ContaInvestimento> consultaTodasContasInvestimento() {
        return repository.findAll().stream().map(correntista -> correntista.getConta().getContaInvestimento()).collect(Collectors.toList());

    }
}
