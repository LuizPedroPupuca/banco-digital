package com.zupedu.bancodigital.domain.cartao_virtual;

import com.zupedu.bancodigital.domain.cartao_virtual.utils.Gerador;
import com.zupedu.bancodigital.domain.correntista.Correntista;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class CartaoVirtualTemporario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numero;

    private String cv;

    @Column(nullable = false)
    private LocalDate dataValidade;

    public Boolean ativo = false;

    @ManyToOne
    private Correntista correntista;

    public CartaoVirtualTemporario(Correntista correntista) {
        this.numero = Gerador.geraNumero();
        this.cv = Gerador.geraCV();
        this.dataValidade = LocalDate.now().plusDays(2);
        this.correntista = correntista;
    }

    @Deprecated
    public CartaoVirtualTemporario() {
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public String getCv() {
        return cv;
    }
}
