package com.zupedu.bancodigital.application.autenticacao;

import com.zupedu.bancodigital.application.autenticacao.input.CredenciaisRequest;
import com.zupedu.bancodigital.application.autenticacao.output.JwtResponse;
import com.zupedu.bancodigital.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<JwtResponse> autentica(@RequestBody @Valid CredenciaisRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getSenha());

        Authentication authentication = authManager.authenticate(authenticationToken);
        String token = tokenService.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(token, "Bearer"));
    }
}
