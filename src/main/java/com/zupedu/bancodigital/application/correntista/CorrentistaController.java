package com.zupedu.bancodigital.application.correntista;

import com.zupedu.bancodigital.application.boleto.output.BoletoResponse;
import com.zupedu.bancodigital.application.correntista.input.AtualizaCorrentistaRequest;
import com.zupedu.bancodigital.application.correntista.input.NovoCorrentistaRequest;
import com.zupedu.bancodigital.application.correntista.output.*;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import com.zupedu.bancodigital.domain.cartao_virtual.FaturaCVR;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.conta.ContaService;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import com.zupedu.bancodigital.domain.correntista.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/correntista")
public class CorrentistaController {
    @Autowired
    private CorrentistaService correntistaService;

    @Autowired
    private ContaService contaService;

    @PostMapping
    public ResponseEntity<?> cadastraCorrentista(@RequestBody List<@Valid NovoCorrentistaRequest>  request,
                                      UriComponentsBuilder uriBuilder) {
        request.forEach(r -> correntistaService.verificaExistenciaDeContaCorrente(r.getDocumento()));
        List<Correntista> correntistas = correntistaService.constroi(request);
        List<Conta> contas = correntistas.stream().map(correntista -> correntista.getConta()).collect(Collectors.toList());
//        List<ContaCorrente> contasCorrente = contas.stream().map(conta -> conta.getContaCorrente()).collect(Collectors.toList());
//        var location = uriBuilder.path("/api/correntista/{idCorrentista}/conta/{idConta}/contaCorrente/{idContaCorrente}")
//                .buildAndExpand(correntista.getId(), conta.getId(), contaCorrente.getId())
//                .toUri();
        return ResponseEntity.ok().body(correntistas.stream().map(correntista -> "Conta do correntista "+correntista.getNome()+": "+correntista.getConta().getNumero()).collect(Collectors.toList()));
    }

    @PatchMapping("/atualiza")
    public ResponseEntity<?> atualizaCorrentista(@AuthenticationPrincipal Correntista correntista, @RequestBody @Valid AtualizaCorrentistaRequest request) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        correntistaService.atualiza(correntistaSalvo, request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/lista-cartoes-virtuais")
    public ResponseEntity<?> listaCartoesVirtuais(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        return ResponseEntity.ok().body(new CartaoVirtualResponse(correntistaSalvo));
    }

    @PatchMapping("/contaPoupanca")
    public ResponseEntity<?> adicionaContaPoupancaAoCorrentista(@AuthenticationPrincipal Correntista correntista,
                                                                UriComponentsBuilder uri) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        Conta conta = correntistaSalvo.getConta();
        correntistaService.verificaExistenciaDeContaPoupanca(conta.getId());
        var contaPoupanca = correntistaService.adicionaContaPoupanca(correntista);
        URI location = uri.path("/api/correntista/{idCorrentista}/conta/{idConta}/contaPoupanca/{idContaPoupanca}")
                .buildAndExpand(correntista.getId(), conta.getId(), contaPoupanca.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

    @PatchMapping("/contaInvestimento")
    public ResponseEntity<?> adicionaContaInvestimentoAoCorrentista(@AuthenticationPrincipal Correntista correntista,
                                                                UriComponentsBuilder uri) {
        Long idConta = correntista.getConta().getId();
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        Conta conta = correntistaSalvo.getConta();
        correntistaService.verificaExistenciaDeContaInvestimento(idConta);
        var contaInvestimento = correntistaService.adicionaContaInvestimento(correntista);
        URI location = uri.path("/api/correntista/{idCorrentista}/conta/{idConta}/contaInvestimento/{idContaInvestimento}")
                .buildAndExpand(correntistaSalvo.getId(), conta.getId(), contaInvestimento.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
    @DeleteMapping("/{documentoCorrentista}")
    public ResponseEntity<?> excluiCorrentista(@PathVariable String documentoCorrentista) {
        var correntista = correntistaService.busca(documentoCorrentista);
        correntistaService.remove(correntista);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/conta")
    public ResponseEntity<?> listaContasDeCorrentista(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        return ResponseEntity.ok().body(new CorrentistaContasResponse(correntistaSalvo));
    }

    @GetMapping("/detalha-correntista")
    public ResponseEntity<?> detalharCorrentista(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        return ResponseEntity.ok().body(new CorrentistaResponse(correntistaSalvo));

    }

    @GetMapping
    public ResponseEntity<?> listaCorrentistas() {
        List<Correntista> correntistas = correntistaService.buscaTodos();
        List<CorrentistaResponse> correntistaResponse =
                correntistas.stream().map(CorrentistaResponse::new).collect(Collectors.toList());
        return ResponseEntity.ok().body(correntistaResponse);
    }

    @GetMapping("/transacoes")
    public ResponseEntity<?> listaTransacoesPorCorrentista(@AuthenticationPrincipal Correntista correntista){
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        return ResponseEntity.ok().body(new ExtratoBancarioResponse(correntistaSalvo.getConta()));
    }

    @PatchMapping("/cartao-recorrente")
    public ResponseEntity<?> geraCartaoRecorrente(@AuthenticationPrincipal Correntista correntista, UriComponentsBuilder uri){
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        CartaoVirtualRecorrente cartaoVirtualRecorrente = correntistaService.constroiCartaoRecorrente(correntistaSalvo);
//        URI location = uri
//                .path("api/correntista/{idCorrentista}/cartao-recorrente/{idCartaoRecorrente}")
//                .buildAndExpand(correntistaSalvo.getId(), cartaoVirtualRecorrente.getId()).toUri();

        return ResponseEntity.ok().body(new CartaoVirtualRecorrenteResponse(cartaoVirtualRecorrente));
    }

    @PatchMapping("/cartao-temporario")
    public ResponseEntity<?> geraCartaoTemporario(@AuthenticationPrincipal Correntista correntista, UriComponentsBuilder uri){
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        CartaoVirtualTemporario cartaoVirtualTemporario = correntistaService.constroiCartaoTemporario(correntistaSalvo);
//        URI location = uri
//                .path("api/correntista/{idCorrentista}/cartao-temporario/{idCartaoTemporario}")
//                .buildAndExpand(correntista.getId(), cartaoVirtualTemporario.getId()).toUri();

        return ResponseEntity.ok().body(new CartaoVirtualTemporarioResponse(cartaoVirtualTemporario));
    }

    @GetMapping("/lista-boletos")
    public ResponseEntity<?> listaBoletosPorCorrentista(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        return ResponseEntity.ok().body(correntistaSalvo.getBoletos()
                .stream().map(BoletoResponse::new)
                .collect(Collectors.toList()));
    }

    @PatchMapping("/bloqueia-cvr")
    public ResponseEntity<?> bloqueiaCartaoRecorrente(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        correntistaService.blooqueiaCartaoCVR(correntistaSalvo);
        return ResponseEntity.ok().body("Cartão bloqueado com sucesso");
    }

    @PatchMapping("/bloqueia-cvr")
    public ResponseEntity<?> geraFaturaCartaoVirtualRecorrente(@AuthenticationPrincipal Correntista correntista) {
        Correntista correntistaSalvo = correntistaService.busca(correntista.getDocumento());
        FaturaCVR fatura = correntistaService.geraFatura(correntistaSalvo);
        return ResponseEntity.ok().body(new FaturaCVRResponse(fatura));
    }



}
