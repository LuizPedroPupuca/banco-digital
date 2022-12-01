package com.zupedu.bancodigital.domain.cartao_virtual;

import java.util.List;

public interface CartaoTemporarioQuerysRepository {
    List<CartaoVirtualTemporario> listaTodosCartoesTemporarios();

    void salvaTudo(List<CartaoVirtualTemporario> cartoesVirtuaisTemporarios);
}
