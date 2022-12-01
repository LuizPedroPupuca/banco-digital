package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.conta.ContaCorrente;
import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import com.zupedu.bancodigital.domain.correntista.Correntista;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CorrentistaContasResponse {
    private String numero;
    private BigDecimal saldoContaCorrente;
    private String saldoContaPoupanca;
    private String saldoContaInvestimento;


  public CorrentistaContasResponse(Correntista correntista){
      numero = correntista.getConta().getNumero();
      saldoContaCorrente = correntista.getConta().getContaCorrente().getSaldo();
      saldoContaPoupanca = retornaSaldoSeExisteContaPoupanca(correntista);
      saldoContaInvestimento = retornaSaldoSeExisteContaInvestimento(correntista);
  }

    public CorrentistaContasResponse() {
    }

    private String retornaSaldoSeExisteContaPoupanca(Correntista correntista){
       return correntista.getConta().getContaPoupanca() == null?"Correntista não possui conta poupança":String.valueOf(correntista
               .getConta()
               .getContaPoupanca().getSaldo());
    }

    private String retornaSaldoSeExisteContaInvestimento(Correntista correntista){
        return correntista.getConta().getContaInvestimento() == null?"Correntista não possui conta investimento":String.valueOf(correntista
                .getConta()
                .getContaInvestimento().getSaldo());
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public String getSaldoContaPoupanca() {
        return saldoContaPoupanca;
    }

    public String getSaldoContaInvestimento() {
        return saldoContaInvestimento;
    }
}