package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.correntista.Correntista;

import java.time.LocalDate;

public class CorrentistaResponse {

    private String nome;
    private LocalDate dataDeNascimento;

    private String documento;
    private String numero;
    public CorrentistaResponse(Correntista correntista) {
        nome = correntista.getNome();
        dataDeNascimento = correntista.getDataDeNascimento();
        documento = correntista.getDocumento();
        numero = correntista.getConta().getNumero();
    }

    public CorrentistaResponse() {
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getNumero() {
        return numero;
    }
}
