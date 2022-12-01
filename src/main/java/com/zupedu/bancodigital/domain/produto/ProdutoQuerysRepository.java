package com.zupedu.bancodigital.domain.produto;

import com.zupedu.bancodigital.application.transacao.input.CompraCreditoRequest;

import java.util.List;

public interface ProdutoQuerysRepository {

    Produto salva(Produto produto);

    List<Produto> listaTudo();

    Produto busca(Long idProduto);
}
