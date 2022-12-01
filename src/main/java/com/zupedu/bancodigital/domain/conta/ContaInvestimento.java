package com.zupedu.bancodigital.domain.conta;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class ContaInvestimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Conta conta;

    @Column(nullable = false)
    private BigDecimal saldo = BigDecimal.valueOf(100);

    private LocalDateTime dataHoraCriacao = LocalDateTime.now();

    public ContaInvestimento(Conta conta) {
        this.conta = conta;
    }

    public ContaInvestimento() {
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

    public Conta getConta() {
        return conta;
    }

    public void aplicaRendimento() {
        saldo = saldo.add(saldo.multiply(BigDecimal.valueOf(0.00000018846)));
    }
}
