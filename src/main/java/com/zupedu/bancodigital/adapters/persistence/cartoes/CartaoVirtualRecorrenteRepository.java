package com.zupedu.bancodigital.adapters.persistence.cartoes;

import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CartaoVirtualRecorrenteRepository extends JpaRepository<CartaoVirtualRecorrente, Long> {

//    boolean existsByNumeroAndCvAndDataValidade(String numeroCartao, String cv, LocalDate validade);

    Optional<CartaoVirtualRecorrente> findByNumeroAndCvAndDataValidade(String numeroCartao, String cv, LocalDate validade);
}
