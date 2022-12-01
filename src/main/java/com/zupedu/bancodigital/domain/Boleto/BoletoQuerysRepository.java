package com.zupedu.bancodigital.domain.Boleto;

import com.zupedu.bancodigital.domain.correntista.Correntista;

public interface BoletoQuerysRepository {
    Boleto salva(Boleto boleto);

    Boleto consulta(String numeroBoleto);
}
