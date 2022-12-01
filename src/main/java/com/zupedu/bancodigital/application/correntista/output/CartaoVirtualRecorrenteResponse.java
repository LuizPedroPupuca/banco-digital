package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;

import java.time.LocalDate;

public class CartaoVirtualRecorrenteResponse {
    private String numero;
    private LocalDate dataValidade;
    private String cv;

    private Boolean ativo;

    public CartaoVirtualRecorrenteResponse(CartaoVirtualRecorrente cartaoVirtualRecorrente) {
        this.numero = cartaoVirtualRecorrente.getNumero();
        this.dataValidade = cartaoVirtualRecorrente.getDataValidade();
        this.cv = cartaoVirtualRecorrente.getCv();
        this.ativo = cartaoVirtualRecorrente.getAtivo();

    }

    public CartaoVirtualRecorrenteResponse() {
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public String getCv() {
        return cv;
    }

    public Boolean getAtivo() {
        return ativo;
    }
}
