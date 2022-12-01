package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;

import java.time.LocalDate;

public class CartaoVirtualTemporarioResponse {

    private String numero;
    private LocalDate dataValidade;
    private String cv;

    public CartaoVirtualTemporarioResponse(CartaoVirtualTemporario cartaoVirtualTemporario) {
        this.numero = cartaoVirtualTemporario.getNumero();
        this.dataValidade = cartaoVirtualTemporario.getDataValidade();
        this.cv = cartaoVirtualTemporario.getCv();

    }

    public CartaoVirtualTemporarioResponse() {
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
}
