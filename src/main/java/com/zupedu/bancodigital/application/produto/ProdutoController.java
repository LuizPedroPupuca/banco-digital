package com.zupedu.bancodigital.application.produto;

import com.zupedu.bancodigital.application.produto.input.NovoProdutoRequest;
import com.zupedu.bancodigital.application.produto.output.ProdutoResponse;
import com.zupedu.bancodigital.domain.produto.Produto;
import com.zupedu.bancodigital.domain.produto.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @PostMapping
    public ResponseEntity<?> cadastra(@RequestBody @Valid NovoProdutoRequest request,
                                      UriComponentsBuilder uri){
        Produto produto = service.constroi(request);
        URI location = uri.path("/api/produto/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<?> listaProdutos(){
        List<Produto> produto = service.consultaTudo();
        return ResponseEntity.ok().body(produto.stream().map(ProdutoResponse::new).collect(Collectors.toList()));
    }
}
