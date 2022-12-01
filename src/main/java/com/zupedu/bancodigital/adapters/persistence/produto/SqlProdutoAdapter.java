package com.zupedu.bancodigital.adapters.persistence.produto;

import com.zupedu.bancodigital.domain.produto.Produto;
import com.zupedu.bancodigital.domain.produto.ProdutoQuerysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class SqlProdutoAdapter implements ProdutoQuerysRepository {

    @Autowired
    ProdutoRepository repository;

    @Override
    public Produto salva(Produto produto) {
        return repository.save(produto);
    }

    @Override
    public List<Produto> listaTudo() {
        return repository.findAll();
    }

    @Override
    public Produto busca(Long idProduto) {
        return repository.findById(idProduto)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }
}
