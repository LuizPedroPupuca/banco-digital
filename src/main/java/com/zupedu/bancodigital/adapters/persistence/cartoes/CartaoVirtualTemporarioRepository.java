package com.zupedu.bancodigital.adapters.persistence.cartoes;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoVirtualTemporarioRepository extends JpaRepository<CartaoVirtualTemporario, Long> {
}
