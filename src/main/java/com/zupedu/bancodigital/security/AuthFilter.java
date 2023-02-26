package com.zupedu.bancodigital.security;

import com.zupedu.bancodigital.adapters.persistence.correntista.CorrentistaRepository;
import com.zupedu.bancodigital.domain.correntista.Correntista;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class AuthFilter extends OncePerRequestFilter {

    private CorrentistaRepository repository;
    private TokenService tokenService;

    public AuthFilter(CorrentistaRepository repository, TokenService tokenService) {
        this.repository = repository;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String conteudoDoHeader = request.getHeader("Authorization");

        Optional<String> optionalToken = getToken(conteudoDoHeader);

        if(optionalToken.isPresent() && tokenService.isTokenValid(optionalToken.get())) {
            autenticarCliente(optionalToken.get());
        }

        filterChain.doFilter(request, response);
    }

    private Optional<String> getToken(String headerContent) {
        if(headerContent != null && headerContent.startsWith("Bearer ")){
            String token = headerContent.substring(7);
            return Optional.of(token);
        }

        return Optional.empty();
    }

    private void autenticarCliente(String token) {
        Long idUsuario = tokenService.getIdUsuario(token);
        Optional<Correntista> optionalUsuario = repository.findById(idUsuario);
        Correntista correntista = optionalUsuario.orElseThrow(InternalError::new);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(correntista, null, correntista.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
