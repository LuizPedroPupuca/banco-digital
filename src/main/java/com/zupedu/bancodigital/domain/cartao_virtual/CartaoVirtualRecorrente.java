package com.zupedu.bancodigital.domain.cartao_virtual;

import com.zupedu.bancodigital.domain.cartao_virtual.utils.Gerador;
import com.zupedu.bancodigital.domain.correntista.Correntista;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class CartaoVirtualRecorrente {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private String cv;

    @Column(nullable = false)
    private LocalDateTime dataHoraCriacao = LocalDateTime.now();
    @Column(nullable = false)
    private LocalDate dataValidade;
    @Column(nullable = false)
    private Boolean ativo = false;
    @ManyToOne
    private Correntista correntista;


    public CartaoVirtualRecorrente(Correntista correntista) {
        this.correntista = correntista;
        this.numero = Gerador.geraNumero();
        this.cv = Gerador.geraCV();
        this.dataValidade = LocalDate.now().plusYears(5);
    }

    @Deprecated
    public CartaoVirtualRecorrente() {
    }

    public Long getId() {
        return id;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getCv() {
        return cv;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public Correntista getCorrentista() {
        return correntista;
    }
}
