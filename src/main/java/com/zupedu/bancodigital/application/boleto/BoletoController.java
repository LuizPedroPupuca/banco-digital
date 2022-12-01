package com.zupedu.bancodigital.application.boleto;

import com.zupedu.bancodigital.application.boleto.output.BoletoResponse;
import com.zupedu.bancodigital.application.boleto.output.PagamentoBoletoResponse;
import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.Boleto.BoletoService;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.correntista.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping
public class BoletoController {

    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private BoletoService boletoService;

    @PostMapping("/api/boleto")
    public ResponseEntity<?> geraBoleto(@AuthenticationPrincipal Correntista correntista,
                                        UriComponentsBuilder uri){
        Boleto boleto = boletoService.constroi(correntista);

        return ResponseEntity.ok().body(new BoletoResponse(boleto));
    }
}
