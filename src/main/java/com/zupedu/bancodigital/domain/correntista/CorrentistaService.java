package com.zupedu.bancodigital.domain.correntista;

import com.zupedu.bancodigital.application.correntista.input.AtualizaCorrentistaRequest;
import com.zupedu.bancodigital.application.correntista.input.NovoCorrentistaRequest;
import com.zupedu.bancodigital.application.correntista.output.CartaoVirtualResponse;
import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import com.zupedu.bancodigital.domain.cartao_virtual.FaturaCVR;
import com.zupedu.bancodigital.domain.conta.Conta;;
import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CorrentistaService{

    @Autowired
    private CorrentistaQuerysRepository repository;

    public List<Correntista> constroi(List<NovoCorrentistaRequest> request) {
        return repository.salvaTudo(request.stream().map(CadastraCorrentistaRequest::paraCorrentista).collect(Collectors.toList()));
    }

    public Correntista busca(String documento) {
        return repository.consulta(documento);
    }


    public ContaPoupanca adicionaContaPoupanca(Correntista correntista) {
        Conta conta = correntista.getConta();
        conta.setContaPoupanca(new ContaPoupanca(conta));
        return repository.salva(correntista).getConta().getContaPoupanca();
    }

    public ContaInvestimento adicionaContaInvestimento(Correntista correntista) {
        Conta conta = correntista.getConta();
        conta.setContaInvestimento(new ContaInvestimento(conta));
        return repository.salva(correntista).getConta().getContaInvestimento();
    }
    public void remove(Correntista correntista) {
        repository.deleta(correntista);
    }

    public List<Correntista> buscaTodos() {
        return repository.consultaTodos();
    }

    public void verificaExistenciaDeContaCorrente(String documento) {
        repository.verifica(documento);
    }

    public void atualiza(Correntista correntista, AtualizaCorrentistaRequest request) {
        correntista.atualiza(request);
        repository.salva(correntista);
    }


    public void verificaExistenciaDeContaPoupanca(Long idConta) {
        repository.verificaContaPoupanca(idConta);
    }

    public void verificaExistenciaDeContaInvestimento(Long idConta) {
        repository.verificaContaInvestimento(idConta);
    }

    public CartaoVirtualRecorrente constroiCartaoRecorrente(Correntista correntista) {
        CartaoVirtualRecorrente cartaoVirtualRecorrente = correntista.adicionaCartaoRecorrente();
        Correntista correntistaSalvo = repository.salva(correntista);
        return cartaoVirtualRecorrente;
    }

    public CartaoVirtualTemporario constroiCartaoTemporario(Correntista correntista) {
        CartaoVirtualTemporario cartaoVirtualTemporario = correntista.adicionaCartaoTemporario();
        Correntista correntistaSalvo = repository.salva(correntista);
        return cartaoVirtualTemporario;
    }

    public void blooqueiaCartaoCVR(Correntista correntistaSalvo) {
        List<CartaoVirtualRecorrente> listaCVR = correntistaSalvo.getCartoesRecorrentes().stream()
                .filter(cvr -> cvr.getAtivo()).collect(Collectors.toList());
        if (listaCVR.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Não existe cartão ativo");
        }
        correntistaSalvo.getCartoesRecorrentes().stream()
                .filter(cvr -> cvr.getAtivo()).forEach(cvr -> cvr.setAtivo(false));
        repository.salva(correntistaSalvo);
    }

    public FaturaCVR geraFatura(Correntista correntistaSalvo) {
        CartaoVirtualRecorrente cartaoRecorrente = correntistaSalvo.getCartoesRecorrentes().stream()
                .filter(cvr -> cvr.getAtivo()).findAny().get();

        FaturaCVR faturaCVR = new FaturaCVR();
    }
}

