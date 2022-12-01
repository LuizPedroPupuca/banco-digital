package com.zupedu.bancodigital.domain.cartao_virtual;

import com.zupedu.bancodigital.domain.conta.ContaInvestimento;
import com.zupedu.bancodigital.domain.conta.ContaPoupanca;
import com.zupedu.bancodigital.domain.conta.ContaService;
import com.zupedu.bancodigital.domain.conta.Rendimento;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zupedu.bancodigital.domain.cartao_virtual.utils.Time.MINUTO;

@Component
public class AtualizaStatusCartao {

    Logger log = LoggerFactory.getLogger(Rendimento.class);

    @Autowired
    private CartaoVirtualRecorrenteService recorrenteService;
    @Autowired
    private CartaoVirtualTemporarioService temporarioService;


    @Scheduled(fixedDelay = MINUTO)
    public void atualizaCartaoVirtual(){
        List<CartaoVirtualRecorrente> cartoesVirtuaisRecorrentes = recorrenteService.buscaTodosCartocesRecorrentes();
        if(cartoesVirtuaisRecorrentes.isEmpty()){

            log.info("A tabela cartão-recorrente não contém registros");
        }else {
            recorrenteService.atualizaStatus(cartoesVirtuaisRecorrentes);
            log.info("Tabela cartao-recorrente atualizada com sucesso");
        }

        List<CartaoVirtualTemporario> cartaoVirtuaisTemporarios = temporarioService.buscaTodosCartoesTemporarios();
        if(cartaoVirtuaisTemporarios.isEmpty()){

            log.info("A tabela cartão-temporário não contém registros");
        }else {
            temporarioService.atualizaStatus(cartaoVirtuaisTemporarios);
            log.info("Tabela cartão-temporário atualizada com sucesso");
        }
    }
}
