package com.zupedu.bancodigital.adapters.persistence.boleto;

import com.zupedu.bancodigital.domain.Boleto.Boleto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoletoRepository extends JpaRepository<Boleto, Long> {
    Optional<Boleto> findByNumero(String numeroBoleto);
}
