package com.zupedu.bancodigital.application.autenticacao.input;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CredenciaisRequest {
    private final String login;
    private final String senha;

}
