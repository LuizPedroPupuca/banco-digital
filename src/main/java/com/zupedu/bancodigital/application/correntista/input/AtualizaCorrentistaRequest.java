package com.zupedu.bancodigital.application.correntista.input;

import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

public class AtualizaCorrentistaRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Past
    private LocalDate dataNascimento;

    @CPF
    private String documento;

    public AtualizaCorrentistaRequest(String nome, LocalDate dataNascimento, String documento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.documento = documento;
    }

    public AtualizaCorrentistaRequest() {
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getDocumento() {
        return documento;
    }
}
