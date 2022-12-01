package com.zupedu.bancodigital.domain.conta;
import com.zupedu.bancodigital.domain.correntista.CorrentistaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.zupedu.bancodigital.domain.cartao_virtual.utils.Time.MINUTO;

@Component
public class Rendimento {

    Logger log = LoggerFactory.getLogger(Rendimento.class);

    @Autowired
    private ContaService service;

    @Scheduled(fixedDelay = MINUTO)
    public void implementaRendimento(){
        List<ContaPoupanca> contasPoupanca = service.buscaTodasContasPoupanca();
        if(contasPoupanca.isEmpty()){

            log.info("A tabela conta-poupança não contém registros");
        }else {
            service.aplicaRendimentoPoupanca(contasPoupanca);
            log.info("Tabela conta-poupança atualizada com sucesso");
        }

        List<ContaInvestimento> contasInvestimento = service.buscaTodasContasInvestimento();
        if(contasInvestimento.isEmpty()){

            log.info("A tabela conta-investimento não contém registros");
        }else {
            service.aplicaRendimentoInvestimento(contasInvestimento);
            log.info("Tabela conta-investimento atualizada com sucesso");
        }
    }
}
