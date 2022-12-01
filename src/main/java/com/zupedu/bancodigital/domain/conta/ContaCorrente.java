package com.zupedu.bancodigital.domain.conta;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ContaCorrente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.valueOf(100);

    private LocalDateTime dataHoraCriacao = LocalDateTime.now();

    @OneToOne
    private Conta conta;

    public ContaCorrente(BigDecimal saldo, Conta conta) {
        this.saldo = saldo;
        this.conta = conta;
    }

    public ContaCorrente() {
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Conta getConta() {
        return conta;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }

    public void debita(BigDecimal valor) {
        if(saldo.compareTo(valor) == -1){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
        saldo = saldo.subtract(valor);
    }

    public void credita(BigDecimal valor) {
        saldo = saldo.add(valor);
    }
}
