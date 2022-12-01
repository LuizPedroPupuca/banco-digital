package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import com.zupedu.bancodigital.domain.correntista.Correntista;

import java.util.List;
import java.util.stream.Collectors;

public class CartaoVirtualResponse {
    List<CartaoVirtualRecorrenteResponse> cartoesVirtuaisrecorrentes;
    public CartaoVirtualResponse(Correntista correntista) {
        cartoesVirtuaisrecorrentes = correntista.getCartoesRecorrentes()
                .stream().map(CartaoVirtualRecorrenteResponse::new).collect(Collectors.toList());

    }

    public CartaoVirtualResponse() {
    }

    public List<CartaoVirtualRecorrenteResponse> getCartoesVirtuaisrecorrentes() {
        return cartoesVirtuaisrecorrentes;
    }


}
