package com.zupedu.bancodigital.domain.cartao_virtual;

import com.zupedu.bancodigital.domain.transacao.TipoTransacao;
import com.zupedu.bancodigital.domain.transacao.Transacao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.zupedu.bancodigital.domain.cartao_virtual.StatusPagamento.*;

@Entity
public class FaturaCVR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraCriacao = LocalDateTime.now();

    private LocalDate dataPagamento;
    private LocalDate vencimento;
    @ManyToOne
    private CartaoVirtualRecorrente cvr;
    private BigDecimal valorTotal;

    private String codigoDeBarras;

    private StatusPagamento status = NAO_PAGO;

    public FaturaCVR() {
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraCriacao() {
        return dataHoraCriacao;
    }


    public LocalDate getVencimento() {
        return vencimento;
    }


    public CartaoVirtualRecorrente getCvr() {
        return cvr;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusPagamento getStatus() {
        return status;
    }
}
