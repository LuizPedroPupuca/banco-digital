package com.zupedu.bancodigital.domain.conta;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import static javax.persistence.GenerationType.IDENTITY;


@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(unique = true)
    private String numero;

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
    private ContaCorrente contaCorrente;

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
    private ContaPoupanca contaPoupanca;

    @OneToOne(mappedBy = "conta", cascade = CascadeType.ALL)
    private ContaInvestimento contaInvestimento;

    @OneToMany(mappedBy = "contaOrigem", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoesEfetuadas = new ArrayList<>();

    @OneToMany(mappedBy = "contaDestino", cascade = CascadeType.REMOVE)
    private List<Transacao> transacoesRecebidas = new ArrayList<>();

    private BigDecimal limiteCredito = BigDecimal.valueOf(2000);

    @OneToOne
    @NotNull
    private Correntista correntista;

    @Column(nullable = false)
    LocalDateTime dataCriacao = LocalDateTime.now();

//    @Column(nullable = false)
//    @Enumerated(STRING)
//    private Tipo tipo;

    /**
     * @deprecated para uso dos frameworks
     */



    public Long getId() {
        return id;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public ContaPoupanca getContaPoupanca() {
        return contaPoupanca;
    }

    public ContaInvestimento getContaInvestimento() {
        return contaInvestimento;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public void setContaPoupanca(ContaPoupanca contaPoupanca) {
        this.contaPoupanca = contaPoupanca;
    }

    public void setContaInvestimento(ContaInvestimento contaInvestimento) {
        this.contaInvestimento = contaInvestimento;
    }

    public void setCorrentista(Correntista correntista) {
        this.correntista = correntista;
    }

    public Correntista getCorrentista() {
        return correntista;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }



    @Override
    public String toString() {
        return new StringJoiner(", ", Conta.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("correntista=" + correntista)
                .toString();
    }

    public void adiconaCorrentista(Correntista correntista) {
        this.setCorrentista(correntista);
    }

    public void adicionaContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
        contaCorrente.setConta(this);
    }

    public List<Transacao> getTransacoesEfetuadas() {
        return transacoesEfetuadas;
    }

    public List<Transacao> getTransacoesRecebidas() {
        return transacoesRecebidas;
    }
}
