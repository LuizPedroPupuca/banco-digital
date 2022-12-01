package com.zupedu.bancodigital.domain.correntista;
import com.zupedu.bancodigital.application.correntista.output.CartaoVirtualResponse;
import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;

import java.util.List;

public interface CorrentistaQuerysRepository {
    List<Correntista> salvaTudo(List<Correntista> correntistas);

    Correntista salva(Correntista correntista);

    Correntista consulta(String documento);


    void deleta(Correntista correntista);

    List<Correntista> consultaTodos();

    Boolean verifica(String documento);

    void verificaContaPoupanca(Long idConta);

    void verificaContaInvestimento(Long idConta);

    List<ContaPoupanca> consultaTodasContasPoupanca();

    List<ContaInvestimento> consultaTodasContasInvestimento();

}
