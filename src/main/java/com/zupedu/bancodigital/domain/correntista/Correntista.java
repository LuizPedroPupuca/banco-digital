package com.zupedu.bancodigital.domain.correntista;

import com.zupedu.bancodigital.application.correntista.input.AtualizaCorrentistaRequest;
import com.zupedu.bancodigital.domain.Boleto.Boleto;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualRecorrente;
import com.zupedu.bancodigital.domain.cartao_virtual.CartaoVirtualTemporario;
import com.zupedu.bancodigital.domain.conta.Conta;
import com.zupedu.bancodigital.domain.correntista.utils.SenhaLimpa;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "correntistas")
public class Correntista implements UserDetails {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private LocalDate dataDeNascimento;
    @Column(unique = true)
    private String documento;
    @OneToOne(mappedBy = "correntista", cascade = CascadeType.ALL)
    private Conta conta;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CartaoVirtualRecorrente> cartoesRecorrentes = new ArrayList<>();

    @OneToMany(mappedBy = "correntista", cascade = CascadeType.ALL)
    private List<CartaoVirtualTemporario> cartoesTemporarios = new ArrayList<>();

    @OneToMany(mappedBy = "correntista")
    private List<Boleto> boletos = new ArrayList<>();
    @Positive
    private BigDecimal patrimonioDeclarado;

    @Column(nullable = false)
    LocalDateTime dataCriacao = LocalDateTime.now();

    /**
     * @deprecated para frameworks apenas
     */
    @Deprecated
    public Correntista() { }

    public Correntista(String nome,
                       LocalDate dataDeNascimento,
                       String documento,
                       SenhaLimpa senha,
                       BigDecimal patrimonioDeclarado) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
        this.documento = documento;
        this.senha = senha.getSenhaEncodada();
        this.patrimonioDeclarado = patrimonioDeclarado;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public String getDocumento() {
        return documento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Correntista that = (Correntista) o;
        return Objects.equals(documento, that.documento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documento);
    }

    public void adicionaConta(Conta conta) {
        conta.setCorrentista(this);
        this.conta = conta;

    }

    public void atualiza(AtualizaCorrentistaRequest request) {
        this.setNome(request.getNome());
        this.setDataDeNascimento(request.getDataNascimento());
        this.setDocumento(request.getDocumento());
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Conta getConta() {
        return conta;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public List<CartaoVirtualTemporario> getCartoesTemporarios() {
        return cartoesTemporarios;
    }

    public List<CartaoVirtualRecorrente> getCartoesRecorrentes() {
        return cartoesRecorrentes;
    }

    public List<Boleto> getBoletos() {
        return boletos;
    }

    public CartaoVirtualRecorrente adicionaCartaoRecorrente() {
            for (CartaoVirtualRecorrente cartaoRecorrente: cartoesRecorrentes) {
                if (cartaoRecorrente.getAtivo().equals(true)) {
//                    throw new ResponseStatusException(HttpStatus
//                            .UNPROCESSABLE_ENTITY, "Correntista já possui cartão recorrente ativo");
                    return cartaoRecorrente;
                }
            }

        CartaoVirtualRecorrente cartaoVirtualRecorrente = new CartaoVirtualRecorrente(this);
        cartaoVirtualRecorrente.setAtivo(true);
        cartoesRecorrentes.add(cartaoVirtualRecorrente);
        return cartaoVirtualRecorrente;

    }

    public CartaoVirtualTemporario adicionaCartaoTemporario() {
        CartaoVirtualTemporario cartaoVirtualTemporario = new CartaoVirtualTemporario(this);
        cartaoVirtualTemporario.setAtivo(true);
        cartoesTemporarios.add(cartaoVirtualTemporario);
        return cartaoVirtualTemporario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.conta.getNumero();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
