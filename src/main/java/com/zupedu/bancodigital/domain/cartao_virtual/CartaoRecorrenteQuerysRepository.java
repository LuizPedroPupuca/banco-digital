package com.zupedu.bancodigital.domain.cartao_virtual;

import java.time.LocalDate;
import java.util.List;

public interface CartaoRecorrenteQuerysRepository {

    List<CartaoVirtualRecorrente> listaTodosCartoesRecorrentes();

    void salvaTudo(List<CartaoVirtualRecorrente> cartoesVirtuaisRecorrentes);

    CartaoVirtualRecorrente retornaCvr(String numeroCartao, String cv, LocalDate validade);
}
