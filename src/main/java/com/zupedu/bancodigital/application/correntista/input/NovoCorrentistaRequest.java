package com.zupedu.bancodigital.application.correntista.input;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.conta.ContaCorrente;
import com.zupedu.bancodigital.domain.correntista.CadastraCorrentistaRequest;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.correntista.utils.SenhaLimpa;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;

public class NovoCorrentistaRequest implements CadastraCorrentistaRequest {
    @NotBlank
    private String nome;
    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeNascimento;

    @NotBlank (message = "o campo documento é obrigatório")
    @CPF(message = "o campo documento deve ser um CPF válido")
    private String documento;

    @NotBlank @Size(min = 8)
    private String senha;

    @Positive
    private BigDecimal patrimonioDeclarado;

    public NovoCorrentistaRequest(String nome, LocalDate dataDeNascimento, String documento, String senha, BigDecimal patrimonioDeclarado) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.documento = documento;
        this.senha = senha;
        this.patrimonioDeclarado = patrimonioDeclarado;
    }

    @JsonCreator(mode = PROPERTIES)


    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    public String getSenha() {
        return senha;
    }

    public BigDecimal getPatrimonioDeclarado() {
        return patrimonioDeclarado;
    }

    public Correntista paraCorrentista() {
        Correntista correntista = new Correntista(nome, dataDeNascimento, documento, new SenhaLimpa(senha), patrimonioDeclarado);
        Conta conta = new Conta();
        conta.adicionaContaCorrente(new ContaCorrente());
        correntista.adicionaConta(conta);
        return correntista;
    }
}
