package com.zupedu.bancodigital.security;

import com.zupedu.bancodigital.domain.correntista.Correntista;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.secret}")
    private String secret;

    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

    public Long getExpiration() {
        return expiration;
    }

    public String generateToken(Authentication authentication) {
        Correntista loggedIn = (Correntista) authentication.getPrincipal();
        Instant expiration = Instant.now().plusSeconds(this.expiration * 60);

        return Jwts.builder()
                .setIssuer("Mercado Livre")
                .setSubject(loggedIn.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiration))
                .signWith(this.algorithm, this.secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getIdUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
