package com.zupedu.bancodigital.application.autenticacao;

import com.zupedu.bancodigital.application.autenticacao.input.CredenciaisRequest;
import com.zupedu.bancodigital.application.autenticacao.output.JwtResponse;
import com.zupedu.bancodigital.security.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;

    @PostMapping
    public JwtResponse autentica(@RequestBody @Valid CredenciaisRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha());

        Authentication authentication = authManager.authenticate(authenticationToken);
        return new JwtResponse(tokenService.generateToken(authentication), "Bearer");
    }
}
