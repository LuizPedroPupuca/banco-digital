package com.zupedu.bancodigital.application.correntista.output;

import com.zupedu.bancodigital.application.boleto.output.PagamentoBoletoResponse;
import com.zupedu.bancodigital.application.transacao.output.SaqueResponse;
import com.zupedu.bancodigital.application.transacao.output.TransferenciaEfetuadaResponse;
import com.zupedu.bancodigital.application.transacao.output.TransferenciaRecebidaResponse;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.transacao.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExtratoBancarioResponse {

    LocalDateTime dataHoraConsulta = LocalDateTime.now();
    private String numero;
    private BigDecimal saldoContaCorrente;
    private BigDecimal valorTotalMovimentacao;
    private List<TransferenciaEfetuadaResponse> transferenciasEfetuadas = new ArrayList<>();
    private List<TransferenciaRecebidaResponse> transferenciasRecebidas = new ArrayList<>();
    private List<SaqueResponse> saquesEfetuados = new ArrayList<>();

    private List<CompraCreditoResponse> comprasCreditosEfetuadas = new ArrayList<>();

    private List<PagamentoBoletoResponse> boletosPagos = new ArrayList<>();




    public ExtratoBancarioResponse(Conta conta){
        numero = conta.getNumero();
        saldoContaCorrente = conta.getContaCorrente().getSaldo();
        valorTotalMovimentacao = retornaValorTotalMovimentacao(conta);
        transferenciasEfetuadas = retornaTransferenciasEfetuadas(conta);
        transferenciasEfetuadas.forEach(transacao -> transacao.setValor("-"+transacao.getValor()));
        transferenciasRecebidas = retornaTransferenciasRecebidas(conta);
        transferenciasRecebidas.forEach(transacao -> transacao.setValor("+"+transacao.getValor()));
        comprasCreditosEfetuadas = retornaComprasCreditosEfetuadas(conta);
        comprasCreditosEfetuadas.forEach(transacao -> transacao.setValor("-"+transacao.getValor()));
        saquesEfetuados = retornaSaquesEfetuados(conta);
        saquesEfetuados.forEach(transacao -> transacao.setValor("-"+transacao.getValor()));
        boletosPagos = retornaValorBoletosPagos(conta);
        boletosPagos.forEach(transacao -> transacao.setValor("-"+transacao.getValor()));
    }

    public ExtratoBancarioResponse() {
    }

    private List<TransferenciaEfetuadaResponse> retornaTransferenciasEfetuadas(Conta conta){
        return conta.getTransacoesEfetuadas().stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.TRANSFERENCIA))
                .map(TransferenciaEfetuadaResponse::new).collect(Collectors.toList());

    }

    private List<SaqueResponse> retornaSaquesEfetuados(Conta conta){
        return conta.getTransacoesEfetuadas().stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.SAQUE))
                .map(SaqueResponse::new).collect(Collectors.toList());
    }

    private List<TransferenciaRecebidaResponse> retornaTransferenciasRecebidas(Conta conta){
        return conta.getTransacoesRecebidas().stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.TRANSFERENCIA))
                .map(TransferenciaRecebidaResponse::new).collect(Collectors.toList());
    }

    private List<CompraCreditoResponse> retornaComprasCreditosEfetuadas(Conta conta){
        return conta.getTransacoesEfetuadas().stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.COMPRA_CREDITO))
                .map(CompraCreditoResponse::new).collect(Collectors.toList());
    }

    private BigDecimal retornaValorTotalMovimentacao(Conta conta){
        BigDecimal valorTransferenciasEfetuadas = retornaValorTransferenciasEfetuadas(conta);
        BigDecimal valorTransferenciasRecebidas = retornaValorTransferenciasRecebidas(conta);
        BigDecimal valorSaquesEfetuados = retornaValorSaques(conta);
        BigDecimal valorPagamentos = retornaPagamentos(conta);
        BigDecimal valorComprasCreditosEfetuadas = retornaValorComprasCreditoEfetuadas(conta);
        return valorSaquesEfetuados.add(valorTransferenciasEfetuadas)
                .add(valorTransferenciasRecebidas).add(valorPagamentos).add(valorComprasCreditosEfetuadas);
    }

    public BigDecimal retornaValorTransferenciasEfetuadas(Conta conta){
        return conta.getTransacoesEfetuadas()
                .stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.TRANSFERENCIA))
                .map(transacao -> transacao.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal retornaValorTransferenciasRecebidas(Conta conta){
        return conta.getTransacoesRecebidas()
                .stream()
                .map(transacao -> transacao.getValor())
                .reduce(BigDecimal.ZERO,BigDecimal::add);
    }

    public BigDecimal retornaValorSaques(Conta conta){
        return conta.getTransacoesEfetuadas()
                .stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.SAQUE))
                .map(transacao -> transacao.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal retornaValorComprasCreditoEfetuadas(Conta conta){
        return conta.getTransacoesEfetuadas()
                .stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.COMPRA_CREDITO))
                .map(transacao -> transacao.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal retornaPagamentos(Conta conta){
        return conta.getTransacoesEfetuadas()
                .stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.BOLETO))
                .map(transacao -> transacao.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<PagamentoBoletoResponse> retornaValorBoletosPagos(Conta conta){
        return conta.getTransacoesEfetuadas()
                .stream()
                .filter(transacao -> transacao.getTipoTransacao()
                        .equals(TipoTransacao.BOLETO))
                .map(PagamentoBoletoResponse::new).collect(Collectors.toList());
    }


    public LocalDateTime getDataHoraConsulta() {
        return dataHoraConsulta;
    }

    public String getNumero() {
        return numero;
    }

    public BigDecimal getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public BigDecimal getValorTotalMovimentacao() {
        return valorTotalMovimentacao;
    }

    public List<TransferenciaEfetuadaResponse> getTransferenciasEfetuadas() {
        return transferenciasEfetuadas;
    }

    public List<TransferenciaRecebidaResponse> getTransferenciasRecebidas() {
        return transferenciasRecebidas;
    }

    public List<SaqueResponse> getSaquesEfetuados() {
        return saquesEfetuados;
    }

    public List<PagamentoBoletoResponse> getBoletosPagos() {
        return boletosPagos;
    }

    public List<CompraCreditoResponse> getComprasCreditosEfetuadas() {
        return comprasCreditosEfetuadas;
    }
}
