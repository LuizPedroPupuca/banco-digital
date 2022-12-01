package com.zupedu.bancodigital.application.transacao;

import com.zupedu.bancodigital.application.boleto.output.PagamentoBoletoResponse;
import com.zupedu.bancodigital.application.transacao.input.CompraCreditoRequest;
import com.zupedu.bancodigital.application.transacao.input.CompraDebitoRequest;
import com.zupedu.bancodigital.domain.Boleto.BoletoService;
import com.zupedu.bancodigital.application.transacao.input.NovaTransferenciaRequest;
import com.zupedu.bancodigital.application.transacao.input.NovoSaqueRequest;
import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.conta.ContaService;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.correntista.CorrentistaService;
import com.zupedu.bancodigital.domain.transacao.Transacao;
import com.zupedu.bancodigital.domain.transacao.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/transacao")
public class TransacaoController {
    @Autowired
    private TransacaoService service;

    @Autowired
    private ContaService contaService;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private CorrentistaService correntistaService;

    @PatchMapping("transferencia/{documentoDestino}/contaDestino")
    public ResponseEntity<?> transfere(@AuthenticationPrincipal Correntista correntista,
                                       @PathVariable String documentoDestino,
                                       @RequestBody @Valid NovaTransferenciaRequest request,
                                       UriComponentsBuilder uri){

        Conta contaOrigem = correntistaService.busca(correntista.getDocumento()).getConta();
        Conta contaDestino = correntistaService.busca(documentoDestino).getConta();

        Transacao transferencia = service.transfere(contaOrigem, contaDestino, request);
        URI location = uri
                .path("/api/transacao/transferencia/{idTransferencia}/contaOrigem/{idContaOrigem}/contaDestino/{idContaDestino}")
                .buildAndExpand(transferencia.getId(), transferencia.getContaOrigem()
                        .getId(), transferencia.getContaDestino().getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("saque/conta")
    public ResponseEntity<?> saque(@AuthenticationPrincipal Correntista correntista,
                                       @RequestBody @Valid NovoSaqueRequest request,
                                       UriComponentsBuilder uri){

        Conta conta = correntistaService.busca(correntista.getDocumento()).getConta();
        Transacao saque = service.saca(conta, request);
        URI location = uri
                .path("/api/transacao/saque/{idTransferencia}/contaOrigem/{idContaOrigem}")
                .buildAndExpand(saque.getId(), saque.getContaOrigem()
                        .getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("pagamento/conta/{numeroBoleto}/boleto")
    public ResponseEntity<?> pagaBoleto(@AuthenticationPrincipal Correntista correntista,
                                        @PathVariable String numeroBoleto,
                                        UriComponentsBuilder uri){

        Conta contaDestino = correntistaService.busca(correntista.getDocumento()).getConta();
        Boleto boleto = boletoService.busca(numeroBoleto);
        Transacao pagamento = service.paga(boleto, contaDestino);
        return ResponseEntity.ok().body(new PagamentoBoletoResponse(pagamento));
    }

    @PatchMapping("pagamento/compra-credito")
    public ResponseEntity<?> compraCredito(@RequestBody @Valid CompraCreditoRequest request,
                                           UriComponentsBuilder uri){
        Transacao compra = service.efetuaCompraCredito(request);

        URI location = uri.path("/api/transacao/pagamento/compra-credito/{id}").buildAndExpand(compra.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

//    @PatchMapping("pagamento/compra-debito")
//    public ResponseEntity<?> compraDebito(@RequestBody @Valid CompraDebitoRequest request,
//                                           UriComponentsBuilder uri){
//        Transacao compra = service.efetuaCompraDebito(request);
//
//        URI location = uri.path("/api/transacao/pagamento/compra-debito/{id}").buildAndExpand(compra.getId()).toUri();
//
//        return ResponseEntity.created(location).build();
//    }

}
