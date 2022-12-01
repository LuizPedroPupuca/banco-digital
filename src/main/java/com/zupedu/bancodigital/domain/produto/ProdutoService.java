package com.zupedu.bancodigital.domain.produto;

import com.zupedu.bancodigital.application.produto.input.NovoProdutoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    ProdutoQuerysRepository repository;
    public Produto constroi(ProdutoRequest request) {
        return repository.salva(request.toModel());
    }

    public List<Produto> consultaTudo() {
        return repository.listaTudo();
    }
}
