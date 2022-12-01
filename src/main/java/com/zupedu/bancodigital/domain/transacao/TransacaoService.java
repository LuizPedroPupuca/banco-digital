package com.zupedu.bancodigital.domain.transacao;

import com.zupedu.bancodigital.application.transacao.input.CompraCreditoRequest;
import com.zupedu.bancodigital.application.transacao.input.NovaTransferenciaRequest;
import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.Boleto.Status;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoRecorrenteQuerysRepository;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.produto.Produto;
import com.zupedu.bancodigital.domain.produto.ProdutoQuerysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static com.zupedu.bancodigital.domain.Boleto.Status.PAGO;
import static com.zupedu.bancodigital.domain.transacao.TipoTransacao.*;

@Service
public class
TransacaoService {

    @Autowired
    private TransacaoQuerysRepository repository;

    @Autowired
    private ProdutoQuerysRepository produtoRepository;

    @Autowired
    private CartaoRecorrenteQuerysRepository cvrRepository;
    public Transacao transfere(Conta contaOrigem, Conta contaDestino, TransferenciaRequest request) {
        Transacao transferencia = request.toModel(contaOrigem, contaDestino);
        return repository.salva(transferencia);
    }

    public Transacao saca(Conta contaOrigem, SaqueRequest request) {
        Transacao transacao = request.toModel(contaOrigem);
        transacao.setTipoTransacao(SAQUE);
        return repository.salva(transacao);
    }

    public Transacao paga(Boleto boleto, Conta conta) {
        if(boleto.getStatus().equals(PAGO)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Boleto já pago");
        }
        Transacao pagamento = new Transacao(conta, boleto.getValor());
        conta.getContaCorrente().debita(boleto.getValor());
        boleto.setStatus(PAGO);
        pagamento.setTipoTransacao(BOLETO);
        pagamento.setNumeroBoleto(boleto.getNumero());
        return repository.salva(pagamento);
    }

    public Transacao efetuaCompraCredito(CompraRequest request) {
        CompraCreditoRequest creditoReqquest = (CompraCreditoRequest) request;
        CartaoVirtualRecorrente cvr = cvrRepository.retornaCvr(creditoReqquest.getNumeroCartao(),
                creditoReqquest.getCv(), creditoReqquest.getValidade());
        valida(cvr);
        Produto produto = produtoRepository
                .busca(creditoReqquest.getIdProduto());
        produto.descrementa(creditoReqquest.getQtde());
//        produtoRepository.salva(produto);
        Transacao compra = request.toModel(produto, cvr.getCorrentista().getConta());
        compra.setTipoTransacao(COMPRA_CREDITO);
        return repository.salva(compra);
    }

//    public Transacao efetuaCompraDebito(CompraRequest request) {
//        CompraCreditoRequest creditoReqquest = (CompraCreditoRequest) request;
//        CartaoVirtualRecorrente cvr = cvrRepository.retornaCvr(creditoReqquest.getNumeroCartao(),
//                creditoReqquest.getCv(), creditoReqquest.getValidade());
//        valida(cvr);
//        Produto produto = produtoRepository
//                .busca(creditoReqquest.getIdProduto());
//        produto.descrementa(creditoReqquest.getQtde());
////        produtoRepository.salva(produto);
//        Transacao compra = request.toModel(produto, cvr.getCorrentista().getConta());
//        compra.setTipoTransacao(COMPRA_CREDITO);
//        return repository.salva(compra);
//    }

    private void valida(CartaoVirtualRecorrente cvr) {
        if(cvr.getAtivo().equals(false)){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão não está ativo");
        }
    }
}
