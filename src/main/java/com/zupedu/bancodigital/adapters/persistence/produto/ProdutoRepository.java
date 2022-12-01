package com.zupedu.bancodigital.adapters.persistence.produto;

import com.zupedu.bancodigital.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
