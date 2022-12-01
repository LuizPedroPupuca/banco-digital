package com.zupedu.bancodigital.security;

import com.zupedu.bancodigital.adapters.persistence.correntista.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final String[] GET_AUTHENTICATED = {"/api/correntista/lista-cartoes-virtuais",
            "/api/correntista", "/api/correntista/conta",
            "/api/correntista/detalha-correntista",
            "/api/correntista/transacoes","/api/correntista/lista-boletos"};

    private final String[] GET_PUBLIC = {"/api/correntista/contas","/api/correntista","/h2-console/","/api/produto"};

    private final String[] POST_AUTHENTICATED = {"/api/boleto"};
    private final String[] PATCH_AUTHENTICATED = {"/api/correntista/contaPoupanca","/api/correntista/contaInvestimento"
            ,"/api/correntista/cartao-recorrente","/api/correntista/cartao-temporario","/api/correntista/atualiza"
            ,"/api/transacao/transferencia/**/contaDestino",
            "/api/transacao/saque/conta",
            "/api/transacao/pagamento/conta/**/boleto","/api/correntista/bloqueia-cvr"};
    private final String[] POST_PUBLIC = {"/api/auth", "/api/correntista","/api/produto"};

    private final String[] DELET_PUBLIC = {"/api/correntista/**"};

    private final String[] PATCH_PUBLIC = {"/api/transacao/pagamento/compra-credito"};



    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().disable();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, POST_PUBLIC).permitAll()
                .antMatchers(HttpMethod.POST, POST_AUTHENTICATED).authenticated()
                .antMatchers(HttpMethod.PATCH, PATCH_AUTHENTICATED).authenticated()
                .antMatchers(HttpMethod.GET, GET_AUTHENTICATED).authenticated()
                .antMatchers(HttpMethod.DELETE, DELET_PUBLIC ).permitAll()
                .antMatchers(HttpMethod.PATCH, PATCH_PUBLIC ).permitAll()
                .antMatchers(HttpMethod.GET, GET_PUBLIC ).permitAll()
                .anyRequest().denyAll();

        http.addFilterBefore(new AuthFilter(correntistaRepository
                , tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {

    }
}
