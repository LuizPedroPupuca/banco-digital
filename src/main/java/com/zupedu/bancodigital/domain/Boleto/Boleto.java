package com.zupedu.bancodigital.domain.Boleto;

import com.zupedu.bancodigital.domain.cartao_virtual.utils.Gerador;
import com.zupedu.bancodigital.domain.correntista.Correntista;

import javax.persistence.*;
import java.math.BigDecimal;

import static com.zupedu.bancodigital.domain.Boleto.Status.PENDENTE;

@Entity
public class Boleto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Correntista correntista;

    @Column(unique = true, nullable = false)
    private String numero;

    @Column(nullable = false)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private Status status = PENDENTE;

    public Boleto(Correntista correntista) {
        this.correntista = correntista;
        this.numero = Gerador.numeroBoleto();
        this.valor = Gerador.valorBoleto();
    }

    @Deprecated
    public Boleto() {
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
